package com.arvato.spring.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;
    @Column(name = "created")
    private OffsetDateTime created;
    @Column(name = "account_from")
    private Integer accountFrom;
    @Column(name = "account_to")
    private Integer accountTo;
    @Column(name = "value")
    private Integer value;
}