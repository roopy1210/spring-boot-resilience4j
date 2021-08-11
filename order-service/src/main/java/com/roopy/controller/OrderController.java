package com.roopy.controller;

import com.roopy.service.OrderService;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class OrderController {

    @Autowired @Qualifier(value = "circuitBreakerService")
    private OrderService circuitBreakerService;

    @Autowired
    @Qualifier(value = "retryService")
    private OrderService retryService;

    @Autowired
    @Qualifier(value = "bulkheadService")
    private OrderService bulkheadService;

    /**
     * CircuitBreaker 테스트를 위한 메소드
     *
     * @return 주문 처리 결과 메세지
     */
    @GetMapping(value = "/order/circuitbreaker")
    public ResponseEntity<String> makeOrderForCircuitBreaker() {

        HashMap<String,String> resultMap = circuitBreakerService.makeOrder();

        if (!"S".equals(resultMap.get("code"))) {
           return new ResponseEntity<>(resultMap.get("msg"), HttpStatus.SERVICE_UNAVAILABLE);
        }

        return new ResponseEntity<>(resultMap.get("msg"), HttpStatus.OK);
    }

    /**
     * Retry 테스트를 위한 메소드
     *
     * @return 주문 처리 결과 메세지
     */
    @GetMapping(value = "/order/retry")
    public ResponseEntity<String> makeOrderForRetry() {
        HashMap<String,String> resultMap = retryService.makeOrder();

        if (!"S".equals(resultMap.get("code"))) {
            return new ResponseEntity<>(resultMap.get("msg"), HttpStatus.SERVICE_UNAVAILABLE);
        }

        return new ResponseEntity<>(resultMap.get("msg"), HttpStatus.OK);
    }

    /**
     * Bulkhead 테스트를 위한 메소드
     *
     * @return 주문 처리 결과 메세지
     */
    @GetMapping(value = "/order/bulkhead")
    public ResponseEntity<String> makeOrderForBulkhead() {
        HashMap<String,String> resultMap = bulkheadService.makeOrder();

        if (!"S".equals(resultMap.get("code"))) {
            return new ResponseEntity<>(resultMap.get("msg"), HttpStatus.TOO_MANY_REQUESTS);
        }

        return new ResponseEntity<>(resultMap.get("msg"), HttpStatus.OK);
    }

}
