package com.arvato.spring.controllers.rest;


import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.AccountRepository;
import com.arvato.spring.repositories.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
class AccountController {

    @Autowired
    private AccountRepository accounts;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private TransactionRepository transactions;
    @Autowired
    private Sender sender;
    @Autowired
    private SimpMessagingTemplate webSocket;

    @PostMapping("")
    public Mono<Account> create(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return this.accounts.save(account);
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
        return this.accounts.findById(id)
    public Mono<Account> update(@PathVariable("id") Integer id, @RequestBody @NonNull Account account) {
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
        return null;
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