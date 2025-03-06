package com.example.processor.controller;

import com.example.processor.config.SecurityTestConfig;
import com.example.processor.dto.TransactionDTO;
import com.example.processor.dto.TransactionUpdateRequest;
import com.example.processor.exception.TransactionNotFoundException;
import com.example.processor.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(SecurityTestConfig.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TransactionService transactionService;

    @Test
    void testUpdateDescription_Success() throws Exception {
        TransactionDTO mockTransaction = new TransactionDTO();
        mockTransaction.setId(1L);
        mockTransaction.setDescription("Updated Description");

        TransactionUpdateRequest updateRequest = new TransactionUpdateRequest();
        updateRequest.setDescription("Updated Description");

        when(transactionService.updateTransactionDescription(1L, "Updated Description"))
                .thenReturn(mockTransaction);

        mockMvc.perform(put("/api/v1/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void testUpdateDescription_NotFound() throws Exception {
        doThrow(new TransactionNotFoundException("Transaction with ID 99 not found"))
                .when(transactionService).updateTransactionDescription(99L, "Updated Description");

        TransactionUpdateRequest updateRequest = new TransactionUpdateRequest();
        updateRequest.setDescription("Updated Description");

        mockMvc.perform(put("/api/v1/transactions/99")
                        .content(new ObjectMapper().writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Transaction with ID 99 not found"));
    }
}


