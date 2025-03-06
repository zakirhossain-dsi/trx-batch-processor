package com.example.processor.service;

import com.example.processor.dto.TransactionDTO;
import com.example.processor.entity.Transaction;
import com.example.processor.exception.TransactionConflictException;
import com.example.processor.exception.TransactionNotFoundException;
import com.example.processor.factory.TransactionFactory;
import com.example.processor.repository.TransactionRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public Page<TransactionDTO> searchTransactions(String customerId, List<String> accountNumbers, String description, Pageable pageable) {

        Specification<Transaction> spec = Specification.where(null);

        if (customerId != null && !customerId.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("customerId"), customerId));
        }
        if (accountNumbers != null && !accountNumbers.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("accountNumber").in(accountNumbers));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
        }

        Page<Transaction> transactionsPage = transactionRepository.findAll(spec, pageable);
        return transactionsPage.map(TransactionDTO::new);
    }

    @Transactional
    public TransactionDTO updateTransactionDescription(Long id, String newDescription) {
        try {
            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found."));

            transaction.setDescription(newDescription);
            transaction = transactionRepository.save(transaction);
            return TransactionFactory.createTransactionDTO(transaction);

        } catch (OptimisticLockingFailureException | StaleObjectStateException e) {
            throw new TransactionConflictException("Concurrent update detected. Please retry.");
        }
    }
}