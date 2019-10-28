package com.transporter.tablemanagement;

import com.transporter.entities.driver.Driver;
import com.transporter.entities.driver.DriverRepository;
import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderRepository;
import com.transporter.entities.order.OrderStatus;
import com.transporter.entities.user.User;
import com.transporter.entities.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrdersBuilder {

    private final static String USER_1 = "john";
    private final static String USER_2 = "james";
    private final static String USER_3 = "tom";

    private final static String DRIVER_1 = "tim";
    private final static String DRIVER_2 = "mark";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DriverRepository driverRepository;

    public void createOrders(){
        insertUsers();
        insertDrivers();
        insertOrders();
    }

    private void insertUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser(USER_1));
        users.add(createUser(USER_2));
        users.add(createUser(USER_3));
        userRepository.saveAll(users);
    }

    private void insertDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(createDriver(DRIVER_1));
        drivers.add(createDriver(DRIVER_2));
        driverRepository.saveAll(drivers);
    }

    private void insertOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder(USER_1, DRIVER_2, OrderStatus.AWAITING_TRIP, -1));
        orders.add(createOrder(USER_1, DRIVER_2, OrderStatus.AWAITING_CONFIRMATION, 2));
        orders.add(createOrder(USER_2, DRIVER_1, OrderStatus.COMPLETE, -3));
        orders.add(createOrder(USER_3, DRIVER_2, OrderStatus.CANCELLED, 2));
        orders.add(createOrder(USER_1, DRIVER_2, OrderStatus.CANCELLED, -2));
        orders.add(createOrder(USER_3, DRIVER_1, OrderStatus.TRIP_ACTIVATED, 0));
        orders.add(createOrder(USER_3, DRIVER_1, OrderStatus.AWAITING_CONFIRMATION, -2));
        orderRepository.saveAll(orders);
    }

    private Order createOrder(String user, String driver, OrderStatus orderStatus, int daysRemainingToTrip) {
        Order order = new Order();
        order.setDriver(driverRepository.findByUsername(driver));
        order.setUser(userRepository.findByUsername(user));
        order.setTripDate(LocalDateTime.now().plusDays(daysRemainingToTrip));
        order.setOrderStatus(orderStatus);
        return order;
    }

    private Driver createDriver(String username) {
        Driver driver = new Driver();
        driver.setUsername(username);
        return driver;
    }

    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
