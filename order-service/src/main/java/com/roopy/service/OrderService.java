package com.roopy.service;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface OrderService {

    public ResponseEntity<String> makeOrder();

}
