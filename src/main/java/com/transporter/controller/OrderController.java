package com.transporter.controller;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderResource;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @PostMapping(value = "/create-order")
    @CrossOrigin(origins = "*")
    public ResponseEntity<OrderResource> createOrder(@RequestBody Order order) {
        return null;
    }

    @GetMapping(value = "/get-order")
    @CrossOrigin(origins = "*")
    public Order getOrder(){
        Order order = new Order();
        return order;
    }
}
