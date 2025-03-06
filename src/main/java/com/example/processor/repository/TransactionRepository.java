package com.example.processor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.processor.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    Page<Transaction> findByCustomerId(String customerId, Pageable pageable);
    Page<Transaction> findByAccountNumberIn(List<String> accountNumbers, Pageable pageable);
    Page<Transaction> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}

