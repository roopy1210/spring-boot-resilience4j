package com.roopy.service.impl;

import com.roopy.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.core.IntervalFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public String makeOrder() {
        return restTemplate.getForObject("http://localhost:7071/pay", String.class);
    }

    public String circuitbreakerFallback(Throwable t) {
        logger.error("Circuitbreaker fallback, cause - {}", t.toString());
        return "[Circuitbreaker] 결제서비스 호출 중 오류가 발생 하였습니다.";
    }

}
