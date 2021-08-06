package com.roopy.controller;

import com.roopy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    @Qualifier(value = "circuitBreakerService")
    private OrderService circuitBreakerService;

    @Autowired
    @Qualifier(value = "retryService")
    private OrderService retryService;

    /**
     * CircuitBreaker 테스트를 위한 메소드
     *
     * @return 주문 처리 결과 메세지
     */
    @GetMapping(value = "/order/circuitbreaker")
    public ResponseEntity<String> makeOrderForCircuitBreaker() {
        return new ResponseEntity<>(circuitBreakerService.makeOrder(), HttpStatus.OK);
    }

    /**
     * Retry 테스트를 위한 메소드
     *
     * @return
     */
    @GetMapping(value = "/order/retry")
    public ResponseEntity<String> makeOrderForRetry() {
        return new ResponseEntity<>(retryService.makeOrder(), HttpStatus.OK);
    }
}
