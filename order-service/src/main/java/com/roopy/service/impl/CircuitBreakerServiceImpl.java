package com.roopy.service.impl;

import com.roopy.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Qualifier("circuitBreakerService")
public class CircuitBreakerServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "orderService", fallbackMethod = "circuitbreakerFallback")
    public ResponseEntity<String> makeOrder() {
        return new ResponseEntity<>(restTemplate.getForObject("http://localhost:7071/pay", String.class), HttpStatus.OK);
    }

    public ResponseEntity<String> circuitbreakerFallback(Throwable t) {
        logger.error("Fallback Execution For Circuit breaker, cause - {}", t.toString());

        return new ResponseEntity<>("결제 처리 중 오류가 발생하였습니다.", HttpStatus.SERVICE_UNAVAILABLE);
    }

}
