package com.transporter.service;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean addOrder(Order order) {
        orderRepository.save(order);
        return true;
    }
}
