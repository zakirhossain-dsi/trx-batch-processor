package com.example.processor.config;

import com.example.processor.component.TransactionItemProcessor;
import com.example.processor.component.TransactionItemWriter;
import com.example.processor.entity.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public Step transactionStep(JobRepository jobRepository,
                                FlatFileItemReader<Transaction> reader,
                                TransactionItemProcessor processor,
                                TransactionItemWriter writer,
                                PlatformTransactionManager transactionManager) {
        return new StepBuilder("transactionStep", jobRepository)
                .<Transaction, Transaction>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job transactionJob(JobRepository jobRepository, Step transactionStep) {
        return new JobBuilder("transactionJob", jobRepository)
                .start(transactionStep)
                .build();
    }
}


