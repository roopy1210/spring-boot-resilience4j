package com.roopy.service.impl;

import com.roopy.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.core.IntervalFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Qualifier("circuitBreakerService")
public class CircuitBreakerServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "orderService", fallbackMethod = "circuitbreakerFallback")
    public HashMap<String, String> makeOrder() {
        HashMap<String,String> resultMap = new HashMap<>();
        resultMap.put("code", "S");
        resultMap.put("msg", restTemplate.getForObject("http://localhost:7071/pay", String.class));

        return resultMap;
    }

    public HashMap<String,String> circuitbreakerFallback(Throwable t) {
        logger.error("Fallback Execution For Circuit breaker, cause - {}", t.toString());

        HashMap<String,String> resultMap = new HashMap<>();
        resultMap.put("code", "E");
        resultMap.put("msg", "결제서비스 호출 중 오류가 발생 하였습니다.");

        return resultMap;
    }

}
