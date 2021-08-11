package com.roopy.service.impl;

import com.roopy.service.OrderService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Qualifier("bulkheadService")
public class BulkheadServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Bulkhead(name = "orderService", fallbackMethod = "bulkheadFallback")
    public HashMap<String, String> makeOrder() {
        HashMap<String,String> resultMap = new HashMap<>();
        resultMap.put("code", "S");
        resultMap.put("msg", restTemplate.getForObject("http://localhost:7071/pay", String.class));

        return resultMap;
    }

    public HashMap<String,String> bulkheadFallback(Throwable t) {
        logger.error("Fallback Execution For Bulkhead, cause - {}", t.toString());

        HashMap<String,String> resultMap = new HashMap<>();
        resultMap.put("code", "E");
        resultMap.put("msg", "결제서비스 호출 중 오류가 발생 하였습니다.");

        return resultMap;
    }
}
