package com.roopy.service.impl;

import com.roopy.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Qualifier("circuitBreakerService")
public class CircuitBreakerOrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    public String makeOrder() {
        return restTemplate.getForObject("http://localhost:7071/pay", String.class);
    }

    public String orderFallback(Throwable t) {
        logger.error("Inside orderFallback, cause - {}", t.toString());
        return "Inside orderFallback method. Some error occurred while calling service for order";
    }

}
