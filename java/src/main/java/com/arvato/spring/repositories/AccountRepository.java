package com.arvato.spring.repositories;

import com.arvato.spring.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;


public interface AccountRepository extends ReactiveCrudRepository<Account, Integer> {
}
