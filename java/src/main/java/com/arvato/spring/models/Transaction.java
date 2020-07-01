package com.arvato.spring.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.OffsetDateTime;

import java.time.OffsetDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction")
public class Transaction {

    @Id
    @Column("transaction_id")
    private Integer transactionId;
    @Column("created")
    private OffsetDateTime created;
    @Column("account_from")
    private Integer accountFrom;
    @Column("account_to")
    private Integer accountTo;
    @Column("value")
    private Integer value;
}