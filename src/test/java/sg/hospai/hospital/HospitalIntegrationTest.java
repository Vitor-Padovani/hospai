package sg.hospai.hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import sg.hospai.TestcontainersConfiguration;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class HospitalIntegrationTest {

    private static final String HOSPITAL = """
            { "name": "Hospital Central", "phone": "4433334444", "cnpj": "12345678000199" }
            """;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDatabase() {
        jdbcTemplate.execute("TRUNCATE hospitals RESTART IDENTITY CASCADE");
    }

    @Test
    void createReturnsCreatedWithIdAndLocation() throws Exception {
        mockMvc.perform(post("/hospitals").contentType(MediaType.APPLICATION_JSON).content(HOSPITAL))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/hospitals/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hospital Central"))
                .andExpect(jsonPath("$.phone").value("4433334444"))
                .andExpect(jsonPath("$.cnpj").value("12345678000199"));
    }

    @Test
    void getReturnsHospital() throws Exception {
        mockMvc.perform(post("/hospitals").contentType(MediaType.APPLICATION_JSON).content(HOSPITAL))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/hospitals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hospital Central"));
    }

    @Test
    void getUnknownHospitalReturnsNotFoundProblem() throws Exception {
        mockMvc.perform(get("/hospitals/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void duplicatedCnpjReturnsConflict() throws Exception {
        mockMvc.perform(post("/hospitals").contentType(MediaType.APPLICATION_JSON).content(HOSPITAL))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/hospitals").contentType(MediaType.APPLICATION_JSON).content(HOSPITAL))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    void missingRequiredFieldsReturnBadRequestListingThem() throws Exception {
        mockMvc.perform(post("/hospitals").contentType(MediaType.APPLICATION_JSON).content("{ \"name\": \"Hospital\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("phone", "cnpj")));
    }
}
