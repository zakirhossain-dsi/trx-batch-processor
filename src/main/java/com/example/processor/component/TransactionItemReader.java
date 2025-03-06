package com.example.processor.component;

import com.example.processor.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransactionItemReader extends FlatFileItemReader<Transaction> {

    private static final Logger logger = LoggerFactory.getLogger(TransactionItemReader.class);

    public TransactionItemReader() {
        setResource(new ClassPathResource("data/dataSource.txt"));
        setLinesToSkip(1);
        setLineMapper(new SkippingEmptyLineMapper());
    }

    @Override
    public Transaction read() throws Exception {
        Transaction transaction;
        do {
            transaction = super.read();
            if (Objects.isNull(transaction)) {
                return null;
            }
        } while (isEmptyTransaction(transaction));

        return transaction;
    }

    private boolean isEmptyTransaction(Transaction transaction) {
        return transaction.getAccountNumber() == null &&
                transaction.getTrxAmount() == null &&
                transaction.getDescription() == null &&
                transaction.getTrxDate() == null &&
                transaction.getTrxTime() == null &&
                transaction.getCustomerId() == null;
    }

    static class SkippingEmptyLineMapper implements LineMapper<Transaction> {

        private final DelimitedLineTokenizer tokenizer;
        private final BeanWrapperFieldSetMapper<Transaction> fieldSetMapper;

        public SkippingEmptyLineMapper() {
            this.tokenizer = new DelimitedLineTokenizer("|");
            this.tokenizer.setNames("accountNumber", "trxAmount", "description", "trxDateStr", "trxTimeStr", "customerId");
            this.fieldSetMapper = new BeanWrapperFieldSetMapper<>();
            this.fieldSetMapper.setTargetType(Transaction.class);
        }

        @Override
        public Transaction mapLine(String line, int lineNumber) throws Exception {

            if (Objects.isNull(line) || line.trim().isEmpty()) {
                logger.info("Skipping empty line at line number: {}", lineNumber);
                return new Transaction();
            }

            FieldSet fieldSet = tokenizer.tokenize(line);

            if (fieldSet.getFieldCount() != 6) {
                logger.info("Skipping invalid line (incorrect number of tokens) at line: {}", lineNumber);
                return new Transaction();
            }

            return fieldSetMapper.mapFieldSet(fieldSet);
        }
    }
}

