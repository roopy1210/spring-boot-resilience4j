package com.roopy.controller;

import com.roopy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService circuitBreakerService;

    @GetMapping(value = "/order/circuitbreaker")
    public ResponseEntity<String> makeOrderForCircuitBreaker() {
        return new ResponseEntity<>(circuitBreakerService.makeOrder(), HttpStatus.OK);
    }

}
