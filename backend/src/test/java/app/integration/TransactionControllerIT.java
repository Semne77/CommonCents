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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TransactionControllerIT {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getTransactions_returns200_andJsonArray() throws Exception {
        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void addTransaction_thenGetTransactions_containsOne() throws Exception {
        Map<String, Object> tx = new HashMap<>();
        tx.put("amount", new BigDecimal("25.50"));
        tx.put("transactionDate", LocalDateTime.now().withNano(0).toString());
        tx.put("merchant", "Starbucks");
        tx.put("category", "Food");
        tx.put("userId", 1);
        tx.put("createdAt", LocalDateTime.now().withNano(0).toString());

        // POST /transactions/add returns void in your controller, so we just check 200/2xx
        mockMvc.perform(post("/transactions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tx)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
