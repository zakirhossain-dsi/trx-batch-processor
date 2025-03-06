package com.example.processor.dto;

import com.example.processor.entity.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private String accountNumber;
    private Double trxAmount;
    private String description;
    private LocalDate trxDate;
    private LocalTime trxTime;
    private String customerId;
    private Integer version;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.accountNumber = transaction.getAccountNumber();
        this.description = transaction.getDescription();
        this.trxAmount = transaction.getTrxAmount();
        this.trxDate = transaction.getTrxDate();
        this.trxTime = transaction.getTrxTime();
        this.customerId = transaction.getCustomerId();
        this.version = transaction.getVersion();
    }

}

