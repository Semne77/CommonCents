package app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GoalControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getGoals_returns200_andJsonArray() throws Exception {
        mockMvc.perform(get("/api/goals/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void addGoal_thenGetGoals_containsOne() throws Exception {
        Map<String, Object> goal = new HashMap<>();
        goal.put("userId", 1);
        goal.put("goalType", "Groceries");
        goal.put("category", "Supermarkets");
        goal.put("targetAmount", new BigDecimal("200.00"));
        goal.put("period", "week");
        goal.put("startDate", LocalDate.now().toString());
        goal.put("endDate", LocalDate.now().plusDays(7).toString());

        mockMvc.perform(post("/api/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goal)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/api/goals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
