package com.transporter.controller;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderResource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/order")
public class OrderController {


    @PostMapping(value = "/create-order")
    @CrossOrigin(origins = "*")
    public ResponseEntity<OrderResource> createOrder(@RequestBody Order order){
        return null;
    }


}
