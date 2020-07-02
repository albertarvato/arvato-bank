package com.arvato.spring.repositories;

import com.arvato.spring.models.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoginRepository extends ReactiveCrudRepository<Account, Integer> {

    @Query("Select * from Account where username = ?username")
    Account loadUserByUsername(String username);
}
