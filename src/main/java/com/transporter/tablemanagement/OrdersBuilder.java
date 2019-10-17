package com.transporter.tablemanagement;

import com.transporter.driver.Driver;
import com.transporter.driver.DriverRepository;
import com.transporter.order.Order;
import com.transporter.order.OrderRepository;
import com.transporter.order.OrderStatus;
import com.transporter.user.User;
import com.transporter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrdersBuilder{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DriverRepository driverRepository;

    public void insertUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());
        userRepository.saveAll(users);
    }

    public void insertDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(createDriver());
        drivers.add(createDriver());
    }

    public void insertOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder(1L, 1L, OrderStatus.AWAITING_TRIP, 1));
        orders.add(createOrder(1L, 2L, OrderStatus.AWAITING_CONFIRMATION, -2));
        orders.add(createOrder(2L, 1L, OrderStatus.COMPLETE, 3));
        orders.add(createOrder(3L, 2L, OrderStatus.CANCELLED, -2));
        orders.add(createOrder(1L, 2L, OrderStatus.CANCELLED, 2));
        orders.add(createOrder(3L, 2L, OrderStatus.TRIP_ACTIVATED, 0));
        orders.add(createOrder(3L, 2L, OrderStatus.AWAITING_CONFIRMATION, 2));
    }

    private Order createOrder(Long userId, Long driverId, OrderStatus orderStatus, int daysAfterTripDate) {
        Order order = new Order();
        order.setDriver(driverRepository.findById(driverId).get());
        order.setUser(userRepository.findById(userId).get());
        return order;
    }

    private Driver createDriver() {
        return new Driver();
    }

    private User createUser() {
        return new User();
    }
}
