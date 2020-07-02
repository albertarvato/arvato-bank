package com.arvato.spring.controllers.rest;

import com.arvato.spring.models.Transaction;
import com.arvato.spring.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(value = "/transaction")
@RequiredArgsConstructor
class TransactionController {

    @Autowired
    private TransactionRepository transactions;

    @PostMapping("")
    public Mono<Transaction> create(@RequestBody Transaction Transaction) {
        return this.transactions.save(Transaction);
    }

    @GetMapping("/{userId}")
    public Flux<Transaction> getAllByUser(@PathVariable("userId") Integer userId) {
        return this.transactions.findAllBy(userId);
    }
}