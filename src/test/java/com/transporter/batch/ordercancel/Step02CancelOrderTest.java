package com.transporter.batch.ordercancel;

import com.transporter.entities.driver.Driver;
import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderStatus;
import com.transporter.entities.user.User;
import com.transporter.entities.user.inbox.UserInbox;
import com.transporter.entities.user.inbox.UserInboxRepository;
import com.transporter.entities.user.message.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class Step02CancelOrderTest {

    @Mock
    private UserInboxRepository userInboxRepository;

    @InjectMocks
    private Step02CancelOrder step02CancelOrder;

    private Order order;



    @BeforeEach
    void init(){
        when(userInboxRepository.findByUserId(1L)).thenReturn(new UserInbox());
        when(userInboxRepository.findByUserId(2L)).thenReturn(new UserInbox());
        when(userInboxRepository.save(any(UserInbox.class))).thenReturn(new UserInbox());
    }

    @Test
    void shouldCancelOrderWithoutNotification() throws Exception {
        Order order = createOrder(FALSE);
        OrderCancelRow orderCancelRow = createOrderCancelRow(order);
        OrderCancelRow resultRow = step02CancelOrder.process(orderCancelRow);

        assertEquals(OrderStatus.CANCELLED, resultRow.getOrder().getOrderStatus());
        assertEquals(FALSE, resultRow.getUserNotified());
        assertEquals(FALSE, resultRow.getDriverNotified());
        assertEquals(TRUE, resultRow.getProcessed());
    }

    @Test
    void shouldCancelOrderAndNotifyUserAndDriver() throws Exception {
        Order order = createOrder(TRUE);
        OrderCancelRow orderCancelRow = createOrderCancelRow(order);
        OrderCancelRow resultRow = step02CancelOrder.process(orderCancelRow);

        assertEquals(OrderStatus.CANCELLED, resultRow.getOrder().getOrderStatus());
        assertEquals(TRUE, resultRow.getUserNotified());
        assertEquals(TRUE, resultRow.getDriverNotified());
        assertEquals(TRUE, resultRow.getProcessed());
    }

    private Order createOrder(boolean shouldNotify) {
        Order order = new Order();
        User customerUser = new User();
        Driver driver = new Driver();
        User driverUser = new User();

        customerUser.setId(1L);
        customerUser.setNotifyUser(shouldNotify);
        driverUser.setId(2L);
        driver.setId(3L);
        driver.setNotifyDriver(shouldNotify);
        driver.setUser(driverUser);
        order.setUser(customerUser);
        order.setDriver(driver);
        order.setOrderStatus(OrderStatus.AWAITING_CONFIRMATION);

        return order;
    }

    private OrderCancelRow createOrderCancelRow(Order order) {
        OrderCancelRow orderCancelRow = new OrderCancelRow();
        orderCancelRow.setOrder(order);
        orderCancelRow.setNotifyDriver(order.getDriver().getNotifyDriver());
        orderCancelRow.setNotifyUser(order.getUser().getNotifyUser());

        return orderCancelRow;
    }
}

