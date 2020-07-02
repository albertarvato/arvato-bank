package com.arvato.spring.configurations;

import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = loginRepository.loadUserByUsername(s);

        MyUserDetails u = new MyUserDetails();
        u.setAccount(account);

        return u;
    }
}
