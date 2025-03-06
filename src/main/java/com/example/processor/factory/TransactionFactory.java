package com.example.processor.factory;

import com.example.processor.dto.TransactionDTO;
import com.example.processor.entity.Transaction;

public class TransactionFactory {
    public static TransactionDTO createTransactionDTO(Transaction transaction) {
        return new TransactionDTO(transaction);
    }
}

