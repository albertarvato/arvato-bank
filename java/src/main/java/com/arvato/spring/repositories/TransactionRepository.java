package com.arvato.spring.repositories;

import com.arvato.spring.models.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {


}
