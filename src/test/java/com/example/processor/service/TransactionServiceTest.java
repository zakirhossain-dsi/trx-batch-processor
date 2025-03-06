package com.example.processor.service;

import com.example.processor.dto.TransactionDTO;
import com.example.processor.entity.Transaction;
import com.example.processor.exception.TransactionNotFoundException;
import com.example.processor.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction mockTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setDescription("Initial Description");
    }

    @Test
    void testUpdateTransactionDescription_Success() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

        TransactionDTO updatedTransaction = transactionService.updateTransactionDescription(1L, "Updated Description");

        assertNotNull(updatedTransaction);
        assertEquals("Updated Description", updatedTransaction.getDescription());
    }

    @Test
    void testUpdateTransactionDescription_NotFound() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TransactionNotFoundException.class, () ->
                transactionService.updateTransactionDescription(99L, "Updated Description"));

        assertEquals("Transaction with ID 99 not found.", exception.getMessage());
    }
}