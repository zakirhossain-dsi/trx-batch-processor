package com.example.processor.component;

import com.example.processor.entity.Transaction;
import com.example.processor.repository.TransactionRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemWriter implements ItemWriter<Transaction> {

    private final TransactionRepository repository;

    public TransactionItemWriter(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(Chunk<? extends Transaction> transactions) throws Exception {
        repository.saveAll(transactions);
    }
}

