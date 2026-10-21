package sg.hospai.ward;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sg.hospai.TestcontainersConfiguration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class WardIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDatabase() {
        jdbcTemplate.execute("TRUNCATE hospitals, wards RESTART IDENTITY CASCADE");
    }

    @Test
    void createWardReturnsCreated() throws Exception {
        createHospital("12345678000199");

        createWard(1, "CARDIOLOGY")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.roomCount").value(0))
                .andExpect(jsonPath("$.bedCount").value(0));
    }

    @Test
    void unknownSpecialityReturnsBadRequest() throws Exception {
        createHospital("12345678000199");

        createWard(1, "ASTROLOGY")
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void missingSpecialityReturnsBadRequest() throws Exception {
        createHospital("12345678000199");

        mockMvc.perform(post("/hospitals/1/wards").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("speciality"));
    }

    @Test
    void sameSpecialityTwiceInSameHospitalReturnsConflict() throws Exception {
        createHospital("12345678000199");
        createWard(1, "CARDIOLOGY").andExpect(status().isCreated());

        createWard(1, "CARDIOLOGY")
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void sameSpecialityInAnotherHospitalIsAllowed() throws Exception {
        createHospital("12345678000199");
        createHospital("98765432000188");
        createWard(1, "CARDIOLOGY").andExpect(status().isCreated());

        createWard(2, "CARDIOLOGY").andExpect(status().isCreated());
    }

    @Test
    void listReturnsHospitalWards() throws Exception {
        createHospital("12345678000199");
        createHospital("98765432000188");
        createWard(1, "CARDIOLOGY");
        createWard(1, "PEDIATRICS");
        createWard(2, "NEUROLOGY");

        mockMvc.perform(get("/hospitals/1/wards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$[1].speciality").value("PEDIATRICS"));
    }

    @Test
    void getHospitalIncludesWardSummary() throws Exception {
        createHospital("12345678000199");
        createWard(1, "CARDIOLOGY");

        mockMvc.perform(get("/hospitals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.wards", hasSize(1)))
                .andExpect(jsonPath("$.wards[0].speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.wards[0].roomCount").value(0))
                .andExpect(jsonPath("$.wards[0].bedCount").value(0));
    }

    @Test
    void unknownHospitalReturnsNotFound() throws Exception {
        createWard(999, "CARDIOLOGY")
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));

        mockMvc.perform(get("/hospitals/999/wards"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    private void createHospital(String cnpj) throws Exception {
        mockMvc.perform(post("/hospitals").contentType(MediaType.APPLICATION_JSON).content("""
                        { "name": "Hospital", "phone": "4433334444", "cnpj": "%s" }
                        """.formatted(cnpj)))
                .andExpect(status().isCreated());
    }

    private ResultActions createWard(long hospitalId, String speciality) throws Exception {
        return mockMvc.perform(post("/hospitals/{id}/wards", hospitalId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"speciality\": \"%s\" }".formatted(speciality)));
    }
}
