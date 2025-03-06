package com.example.processor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private Double trxAmount;
    private String description;
    private LocalDate trxDate;
    private LocalTime trxTime;
    private String customerId;

    @Transient
    private String trxDateStr;
    @Transient
    private String trxTimeStr;

    @Version
    private Integer version;

}

