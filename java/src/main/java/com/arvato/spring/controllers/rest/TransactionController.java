package com.arvato.spring.controllers.rest;

import com.arvato.spring.models.Transaction;
import com.arvato.spring.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/transaction")
@RequiredArgsConstructor
class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("")
    public Transaction create(@RequestBody Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    @GetMapping("")
    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }

    @GetMapping("/from/{id}")
    public List<Transaction> getFrom(@PathVariable("id") Integer fromId) {
        return this.transactionRepository.findAllByAccountFrom(fromId);
    }

    @GetMapping("/to/{id}")
    public List<Transaction> getTo(@PathVariable("id") Integer toId) {
        return this.transactionRepository.findAllByAccountTo(toId);
    }

}