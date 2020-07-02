package com.arvato.spring.controllers.rest;

import com.arvato.spring.jms.Sender;
import com.arvato.spring.jms.SenderSource;
import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/user")
@RequiredArgsConstructor
class AccountController {

    @Autowired
    private AccountRepository accounts;

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
    public Mono<Account> update(@PathVariable("id") Integer id, @RequestBody Account Account) {
        Mono<Account> accountMono = this.accounts.findById(id)
                .map(a -> {
//                    p.setName(Account.setName());
                    return a;
                })
                .flatMap(a -> this.accounts.save(a));

        sender.send(SenderSource.ACCOUNT_UPDATED);
        return accountMono;
    }

    @GetMapping("/balance")
    public Mono<Double> get() {
       sender.send(SenderSource.ACCOUNT_BALANCE_RUN);
        return null;
    }

    @GetMapping("/testJMS")
    public String testJMS(){
        sender.send(SenderSource.ACCOUNT_TEST);
        return "check if in the console the receiver worked";
    }

}