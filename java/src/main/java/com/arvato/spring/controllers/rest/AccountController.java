package com.arvato.spring.controllers.rest;

import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import com.arvato.spring.repositories.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.TimerTask;

@RestController()
@RequestMapping(value = "/user")
@RequiredArgsConstructor
class AccountController {

    @Autowired
    private AccountRepository accounts;
    @Autowired
    private TransactionRepository transactions;


    @Autowired
    private SimpMessagingTemplate webSocket;

    @PostMapping("")
    public Mono<Account> create(@RequestBody Account Account) {
        return this.accounts.save(Account);
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

    @GetMapping("/{id}/startspam")
    public Mono<Double> spam(@PathVariable("id") Integer id) {

        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                webSocket.setDefaultDestination("/topic/balance/" + id.toString());
                webSocket.convertAndSend(Math.random());

            }
        }, 2000, 2000);

        return null;
    }


}