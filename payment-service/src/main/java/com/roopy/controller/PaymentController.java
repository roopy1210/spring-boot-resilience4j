package com.roopy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping(value = "/pay")
    public String makePay() {
        return "Order is completed";
    }

}
