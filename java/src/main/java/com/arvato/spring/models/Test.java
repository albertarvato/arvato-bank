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
@Table("test")
public class Test {

    @Id
    @Column("id")
    private Integer id;
    @Column("title")
    private String title;
    @Column("content")
    private String content;
}