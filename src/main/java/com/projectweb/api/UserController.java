package com.projectweb.api;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/api/login")
    public User user(@RequestParam(value = "clement", defaultValue = "World") String userName) {
        return new User(counter.incrementAndGet(), String.format(template, userName), "aaa", "vnfikjd");
    }

}