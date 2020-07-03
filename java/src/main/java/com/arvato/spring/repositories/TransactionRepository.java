package com.arvato.spring.repositories;

import com.arvato.spring.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByAccountFrom(Integer accountFrom);

    List<Transaction> findAllByAccountTo(Integer accountTo);

}
