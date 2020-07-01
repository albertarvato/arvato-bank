package com.arvato.spring.controllers;

import com.arvato.spring.models.Test;
import com.arvato.spring.repos.TestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController()
@RequestMapping(value = "/test")
@RequiredArgsConstructor
public class TestController {

    private final TestRepo test;
    @GetMapping("")
    public Flux<Test> all() {
        return this.test.findAll();
    }


}
