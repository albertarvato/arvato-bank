package com.arvato.spring.controllers.rest;

import com.arvato.spring.jms.Sender;
import com.arvato.spring.jms.SenderSource;
import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import com.arvato.spring.repositories.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/user")
@RequiredArgsConstructor
class AccountController {

    @Autowired
    private AccountRepository accounts;
    @Autowired
    private TransactionRepository transactions;
    @Autowired
    private Sender sender;

    @PostMapping("")
    public Mono<Account> create(@RequestBody Account Account) {
        Mono<Account> accountMono = this.accounts.save(Account);
        sender.send(SenderSource.ACCOUNT_CREATED);
        return accountMono;
    }

    @GetMapping("/{id}")
    public Mono<Account> get(@PathVariable("id") Integer id) {
        return this.accounts.findById(id);
    }
    @PutMapping("/{id}")
    public Mono<Account> update(@PathVariable("id") Integer id, @RequestBody @NonNull Account account) {
        Mono<Account> accountMono = this.accounts.findById(id)
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
        sender.send(SenderSource.ACCOUNT_UPDATED);
        return accountMono;
    }

    @GetMapping("/{id}/balance")
    public Mono<Double> getBalance(@PathVariable("id") Integer id) {
        Mono<Double> mono = transactions.getBalance(id);
        sender.send(SenderSource.ACCOUNT_BALANCE_RUN);
        return mono;
    }

    @GetMapping("/testJMS")
    public String testJMS(){
        sender.send(SenderSource.ACCOUNT_TEST);
        return "check if in the console the receiver worked";
    }
}