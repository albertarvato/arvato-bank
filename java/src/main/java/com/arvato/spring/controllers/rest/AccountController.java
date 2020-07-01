package com.arvato.spring.controllers.rest;

import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/user")
@RequiredArgsConstructor
class AccountController {
    private final AccountRepository accounts;
    @PostMapping("")
    public Mono<Account> create(@RequestBody Account Account) {
        return this.accounts.save(Account);
    }
    @GetMapping("/{id}")
    public Mono<Account> get(@PathVariable("id") Integer id) {
        return this.accounts.findById(id);
    }
    @PutMapping("/{id}")
    public Mono<Account> update(@PathVariable("id") Integer id, @RequestBody Account Account) {
        return this.accounts.findById(id)
                .map(a -> {
//                    p.setName(Account.setName());
                    return a;
                })
                .flatMap(a -> this.accounts.save(a));
    }

    @GetMapping("/balance")
    public Mono<Double> get() {
        // to implement
    }

}