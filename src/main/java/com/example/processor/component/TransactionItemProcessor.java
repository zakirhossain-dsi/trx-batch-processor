package com.example.processor.component;

import com.example.processor.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction> {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Transaction process(Transaction transaction) {

        transaction.setTrxDate(LocalDate.parse(transaction.getTrxDateStr(), dateFormatter));
        transaction.setTrxTime(LocalTime.parse(transaction.getTrxTimeStr(), timeFormatter));
        return transaction;
    }
}

