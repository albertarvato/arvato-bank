package com.arvato.spring.controllers.rest;


import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import com.arvato.spring.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@CrossOrigin
class AccountController {

    @Autowired
    private AccountRepository accounts;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TransactionRepository transactions;

    @PostMapping("")
    public Mono<Account> create(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return this.accounts.save(account);
    }

    @GetMapping("/{id}")
    public Mono<Account> get(@PathVariable("id") Integer id) {
        return this.accounts.findById(id);
    }
    @PutMapping("/{id}")
    public Mono<Account> update(@PathVariable("id") Integer id, @RequestBody @NonNull Account account) {
        return this.accounts.findById(id)
                .map(a -> {
                    a.setEmail(account.getEmail());
                    a.setFullname(account.getFullname() == null ? a.getFullname() : account.getFullname());
                    a.setIban(account.getIban() == null ? a.getIban() : account.getIban());
                    a.setMobile(account.getMobile()== null ? a.getMobile() : account.getMobile());
                    a.setPassword(account.getPassword() == null ? a.getPassword() : account.getPassword());
                    a.setUsername(account.getUsername() == null ? a.getUsername() : account.getUsername());
                    return a;
                })
                .flatMap(a -> this.accounts.save(a));
    }

    @GetMapping("/{id}/balance")
    public Mono<Double> getBalance(@PathVariable("id") Integer id) {
       return transactions.getBalance(id);
    }

}