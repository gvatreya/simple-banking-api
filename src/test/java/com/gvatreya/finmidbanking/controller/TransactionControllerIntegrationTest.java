package com.gvatreya.finmidbanking.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTransactionsNotSupported() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                ;
    }

    @Test
    public void getTransactionsForAccountThatDoesNotExist() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/uuid-1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
                ;
    }

    @Test
    @Sql(scripts = "classpath:sql/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getTransactionsForAccount() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/uuid_1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionUuid").value("uuid_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(100D))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sourceAccountId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destAccountId").value(2L))
                ;
    }

    @Test
    void getTransactionsForUuid() {
    }

    @Test
    void createTransaction() {
    }
}