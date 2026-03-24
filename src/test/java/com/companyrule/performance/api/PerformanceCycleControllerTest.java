package com.companyrule.performance.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.companyrule.performance.application.OrganizationGateway;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class PerformanceCycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrganizationGateway organizationGateway;

    @BeforeEach
    void setUp() {
        when(organizationGateway.organizationExists(anyString())).thenReturn(true);
        when(organizationGateway.estimateTargetEmployeeCount(anyString())).thenReturn(1);
    }

    @Test
    void createCycleReturnsDraftStatus() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "2026 Mid-Year Review",
                                  "organizationId": "org-001"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("2026 Mid-Year Review"))
                .andExpect(jsonPath("$.organizationId").value("org-001"))
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andReturn();

        JsonNode createdCycle = objectMapper.readTree(result.getResponse().getContentAsString());
        assertThat(createdCycle.get("createdAt").asText()).isEqualTo(createdCycle.get("updatedAt").asText());
    }

    @Test
    void getCycleReturnsCreatedEntity() throws Exception {
        JsonNode createdCycle = createCycleAndReturnBody();
        String cycleId = createdCycle.get("id").asText();

        MvcResult result = mockMvc.perform(get("/api/performance/cycles/{id}", cycleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cycleId))
                .andExpect(jsonPath("$.name").value("2026 Mid-Year Review"))
                .andExpect(jsonPath("$.organizationId").value("org-001"))
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andReturn();

        JsonNode readbackCycle = objectMapper.readTree(result.getResponse().getContentAsString());
        assertThat(readbackCycle.get("createdAt").asText()).isEqualTo(createdCycle.get("createdAt").asText());
        assertThat(readbackCycle.get("updatedAt").asText()).isEqualTo(createdCycle.get("updatedAt").asText());
    }

    @Test
    void getCycleFailsClearlyForMissingCycle() throws Exception {
        mockMvc.perform(get("/api/performance/cycles/{id}", "missing-cycle"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Performance cycle not found: missing-cycle"));
    }

    @Test
    void createCycleFailsValidationForBlankName() throws Exception {
        mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": " ",
                                  "organizationId": "org-001"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }

    @Test
    void createCycleFailsValidationForBlankOrganizationId() throws Exception {
        mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "2026 Mid-Year Review",
                                  "organizationId": " "
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }

    @Test
    void createCycleFailsValidationForMissingNameField() throws Exception {
        mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "organizationId": "org-001"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }

    @Test
    void createCycleFailsValidationForMissingOrganizationIdField() throws Exception {
        mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "2026 Mid-Year Review"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"));
    }

    @Test
    void createCycleFailsClearlyForMissingOrganization() throws Exception {
        when(organizationGateway.organizationExists("missing-org")).thenReturn(false);

        mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "2026 Mid-Year Review",
                                  "organizationId": "missing-org"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Organization not found: missing-org"));
    }

    @Test
    void planGenerationCreatesMinimalResultForExistingCycle() throws Exception {
        JsonNode createdCycle = createCycleAndReturnBody();
        String cycleId = createdCycle.get("id").asText();

        MvcResult result = mockMvc.perform(post("/api/performance/cycles/{id}/plans", cycleId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.cycleId").value(cycleId))
                .andExpect(jsonPath("$.status").value("GENERATED"))
                .andExpect(jsonPath("$.generatedAt").isNotEmpty())
                .andExpect(jsonPath("$.generationMode").value("SYNC_STUB"))
                .andExpect(jsonPath("$.targetEmployeeCount").value(1))
                .andReturn();

        JsonNode plan = objectMapper.readTree(result.getResponse().getContentAsString());
        assertThat(plan.get("generatedAt").asText()).isGreaterThanOrEqualTo(createdCycle.get("createdAt").asText());

        MvcResult cycleReadbackResult = mockMvc.perform(get("/api/performance/cycles/{id}", cycleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cycleId))
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andReturn();

        JsonNode readbackCycle = objectMapper.readTree(cycleReadbackResult.getResponse().getContentAsString());
        assertThat(readbackCycle.get("createdAt").asText()).isEqualTo(createdCycle.get("createdAt").asText());
        assertThat(readbackCycle.get("updatedAt").asText()).isEqualTo(createdCycle.get("updatedAt").asText());
    }

    @Test
    void planGenerationFailsClearlyForMissingCycle() throws Exception {
        mockMvc.perform(post("/api/performance/cycles/{id}/plans", "missing-cycle"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Performance cycle not found: missing-cycle"));
    }

    private String createCycleAndReturnId() throws Exception {
        return createCycleAndReturnBody().get("id").asText();
    }

    private JsonNode createCycleAndReturnBody() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/performance/cycles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "2026 Mid-Year Review",
                                  "organizationId": "org-001"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString());
    }
}
