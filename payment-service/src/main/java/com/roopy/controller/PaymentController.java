package com.roopy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping(value = "/pay")
    public String makePay() {
        return "주문 결제가 정상적으로 처리 되었습니다.";
    }

}
