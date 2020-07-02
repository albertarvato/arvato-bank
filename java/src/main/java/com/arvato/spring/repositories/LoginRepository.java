package com.arvato.spring.repositories;

import com.arvato.spring.models.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoginRepository extends ReactiveCrudRepository<Account, Integer> {

    Mono<Account> findByUsername(String username);
}
