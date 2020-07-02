package com.arvato.spring.repositories;

import com.arvato.spring.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;


public interface AccountRepository extends ReactiveCrudRepository<Account, Integer> {

    @Query("{ 'username': ?0}")
    Mono<UserDetails> loadUserByUsername(String username) throws UsernameNotFoundException;
}
