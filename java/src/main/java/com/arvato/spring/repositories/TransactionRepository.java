package com.arvato.spring.repositories;

import com.arvato.spring.models.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Native;


public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Integer> {

    @Query("select * from transaction t where t.account_from =:userId or account_to=:userId")
    Flux<Transaction> findAllBy(Integer userId);

    @Query("select sum(value) from transaction t where t.account_from =:userId or account_to=:userId")
    Mono<Double> getBalance(Integer id);
}
