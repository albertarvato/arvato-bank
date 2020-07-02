package com.arvato.spring.repositories;

import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository("loginRepo")
public interface LoginRepository extends CrudRepository<Account, Integer>, UserDetailsService {

    @Query("Select * from Account where username = ?username")
    UserDetails loadUserByUsername(String username);
}
