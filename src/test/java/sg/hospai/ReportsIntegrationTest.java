package sg.hospai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ReportsIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    static long zecaId;
    static long bedZeroId;
    static long bedOneId;

    // Single scenario: the reports read the same data, so it is seeded once, in order.
    @Test
    void reports() throws Exception {
        seed();

        mockMvc.perform(get("/beds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].roomCode").value("CARD-1"))
                .andExpect(jsonPath("$[0].hospitalId").isNumber())
                .andExpect(jsonPath("$[4].roomCode").value("PEDI-1"));

        mockMvc.perform(get("/rooms/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$[0].roomCode").value("CARD-1"))
                .andExpect(jsonPath("$[1].roomCode").value("CARD-2"));

        mockMvc.perform(get("/rooms/occupancy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$[0].free").value(2))
                .andExpect(jsonPath("$[0].occupied").value(0))
                .andExpect(jsonPath("$[0].total").value(2))
                .andExpect(jsonPath("$[1].speciality").value("PEDIATRICS"))
                .andExpect(jsonPath("$[1].free").value(0))
                .andExpect(jsonPath("$[1].occupied").value(1))
                .andExpect(jsonPath("$[1].total").value(1));

        mockMvc.perform(get("/admissions/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$[0].patients", hasSize(2)))
                .andExpect(jsonPath("$[0].patients[0].patientName").value("Bia"))
                .andExpect(jsonPath("$[0].patients[1].patientName").value("Zeca"))
                .andExpect(jsonPath("$[0].patients[1].daysAdmitted").value(0))
                .andExpect(jsonPath("$[1].speciality").value("PEDIATRICS"))
                .andExpect(jsonPath("$[1].patients[0].patientName").value("Ana"));

        mockMvc.perform(get("/patients/{id}/current-admission", zecaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hospitalName").value("Hospital Um"))
                .andExpect(jsonPath("$.speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.roomCode").value("CARD-1"))
                .andExpect(jsonPath("$.patientName").value("Zeca"))
                .andExpect(jsonPath("$.admissionAt").isNotEmpty());

        mockMvc.perform(get("/patients/{id}/admissions", zecaId).param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].speciality").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.content[0].dischargeAt").doesNotExist())
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.page.totalPages").value(2));

        mockMvc.perform(get("/patients/{id}/admissions", zecaId).param("size", "1").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].dischargeAt").isNotEmpty());

        mockMvc.perform(get("/beds/{id}/admissions", bedZeroId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].bedNumber").value(1))
                .andExpect(jsonPath("$[0].patientName").value("Zeca"))
                .andExpect(jsonPath("$[0].dischargeAt").doesNotExist())
                .andExpect(jsonPath("$[1].dischargeAt").isNotEmpty());

        mockMvc.perform(get("/beds/{id}/admissions", bedOneId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mockMvc.perform(get("/patients/{id}/admissions", 999999))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/beds/{id}/admissions", 999999))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/patients/{id}/current-admission", 999999))
                .andExpect(status().isNotFound());
    }

    private void seed() throws Exception {
        json(post("/hospitals"), """
                {"name": "Hospital Um", "phone": "4430000000", "cnpj": "12345678000199",
                 "wards": [
                   {"speciality": "CARDIOLOGY", "roomCount": 2, "bedsPerRoom": 2},
                   {"speciality": "PEDIATRICS", "roomCount": 1, "bedsPerRoom": 1}]}
                """).andExpect(status().isCreated());

        long[] beds = new long[5];
        JsonNode all = body(mockMvc.perform(get("/beds")));
        for (int i = 0; i < beds.length; i++) {
            beds[i] = all.get(i).get("id").asLong();
        }
        bedZeroId = beds[0];
        bedOneId = beds[1];

        zecaId = createPatient("Zeca", "11111111111");
        long biaId = createPatient("Bia", "22222222222");
        long anaId = createPatient("Ana", "33333333333");

        // Zeca: admitted, discharged, bed released, admitted again in the same bed
        long firstAdmission = admit(zecaId, beds[0]);
        mockMvc.perform(post("/admissions/{id}/discharge", firstAdmission)).andExpect(status().isOk());
        mockMvc.perform(post("/rooms/{roomId}/beds/{id}/release", 0, beds[0])).andExpect(status().isOk());
        admit(zecaId, beds[0]);

        admit(biaId, beds[2]);
        admit(anaId, beds[4]);
    }

    private long createPatient(String name, String cpf) throws Exception {
        return body(json(post("/patients"), """
                {"name": "%s", "cpf": "%s"}
                """.formatted(name, cpf)).andExpect(status().isCreated())).get("id").asLong();
    }

    private long admit(long patientId, long bedId) throws Exception {
        return body(json(post("/admissions"), """
                {"patientId": %d, "bedId": %d}
                """.formatted(patientId, bedId)).andExpect(status().isCreated())).get("id").asLong();
    }

    private ResultActions json(MockHttpServletRequestBuilder request, String content) throws Exception {
        return mockMvc.perform(request.contentType(MediaType.APPLICATION_JSON).content(content));
    }

    private JsonNode body(ResultActions result) throws Exception {
        return objectMapper.readTree(result.andReturn().getResponse().getContentAsString());
    }
}
