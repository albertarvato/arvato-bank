package com.arvato.spring.controllers.rest;

import com.arvato.spring.error.ResourceNotFoundException;
import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping(value = "/user")
@RequiredArgsConstructor
class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("")
    public Account create(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return this.accountRepository.save(account);
    }

    @GetMapping("/{id}")
    public Optional<Account> get(@PathVariable("id") Integer id) {
        return this.accountRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable("id") Integer id, @RequestBody Account acc) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setUsername(acc.getUsername());
                    account.setEmail(acc.getEmail());
                    account.setFullname(acc.getFullname());
                    account.setIban(acc.getIban());
                    account.setMobile(acc.getMobile());

                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    @GetMapping("/balance")
    public Double get() {
        return null;
    }

}