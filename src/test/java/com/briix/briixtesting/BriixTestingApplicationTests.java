package com.briix.briixtesting;

import com.briix.briixtesting.model.FixedIncomeRequest;
import com.briix.briixtesting.repository.FixedIncomeBankRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BriixTestingApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FixedIncomeBankRepository fixedIncomeBankRepository;

    @Test
    @Order(2)
    @DisplayName("IT: Can Return List Of Fixed Income")
    void getAll() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/admin"));

        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(3)
    @DisplayName("IT: Can Return Single Of Fixed Income")
    void getSingle() throws Exception {
        // Given
        long pathId = 1L;

        // When
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/admin/" + pathId));

        // Then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(1)
    @DisplayName("IT: Can add new Fixed Income")
    void addData() throws Exception {
        // Given
        var payload = FixedIncomeRequest.builder()
                .name("Briix Financial")
                .description("Briix Financial Description")
                .build();

        // When
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)));

        // Then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(payload.getName()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(4)
    @DisplayName("IT: Can update Fixed Income")
    void editData() throws Exception {
        // Given
        long pathId = 1L;

        var payload = FixedIncomeRequest.builder()
                .name("Briix Financial For Update")
                .description("Briix Financial Description")
                .build();

        // When
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/admin/" + pathId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)));

        // Then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(payload.getName()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(4)
    @DisplayName("IT: Can delete Fixed Income (deleted = true)")
    void deleteData() throws Exception {
        // Given
        long pathId = 1L;
        // When
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/v1/admin/" + pathId));

        // Then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.deleteStatus").value("Success"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
