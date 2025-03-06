package com.example.processor.controller;

import com.example.processor.dto.TransactionDTO;
import com.example.processor.dto.TransactionUpdateRequest;
import com.example.processor.exception.TransactionConflictException;
import com.example.processor.exception.TransactionNotFoundException;
import com.example.processor.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Controller", description = "APIs for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public Page<TransactionDTO> searchTransactions(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountNumbers,
            @RequestParam(required = false) String description,
            @PageableDefault(size = 10, sort = "trxDate") Pageable pageable) {

        List<String> accountNumberList = (accountNumbers != null && !accountNumbers.isEmpty())
                ? Arrays.asList(accountNumbers.split(",")) : null;

        return transactionService.searchTransactions(customerId, accountNumberList, description, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDescription(@PathVariable Long id, @RequestBody TransactionUpdateRequest request) {
        TransactionDTO updatedTransaction = transactionService.updateTransactionDescription(id, request.getDescription());
        return ResponseEntity.ok(updatedTransaction);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleTransactionNotFound(TransactionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + ex.getMessage() + "\"}");
    }

    @ExceptionHandler(TransactionConflictException.class)
    public ResponseEntity<?> handleTransactionConflict(TransactionConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"error\": \"" + ex.getMessage() + "\"}");
    }

}