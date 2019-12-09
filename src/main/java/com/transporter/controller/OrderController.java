package com.transporter.controller;

import com.transporter.controller.requests.CreateOrderRequest;
import com.transporter.entities.order.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {

    @PostMapping(value = "/add-order")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return null;
    }

    @GetMapping(value = "/get-order")
    @CrossOrigin(origins = "*")
    public Order getOrder() {
        Order order = new Order();
        return order;
    }
}
