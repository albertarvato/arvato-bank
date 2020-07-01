package com.arvato.spring.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("account")
public class Account {

    @Id
    @Column("account_id")
    private Integer accountId;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("fullname")
    private String fullname;
    @Column("mobile")
    private String mobile;
    @Column("email")
    private String email;
    @Column("iban")
    private String iban;
}