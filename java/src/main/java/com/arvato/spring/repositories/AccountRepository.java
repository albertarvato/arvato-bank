package com.arvato.spring.repositories;

import com.arvato.spring.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String username) throws UsernameNotFoundException;
}
