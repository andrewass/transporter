package com.transporter.batch.ordercancel;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Step02CancelOrderTest {

    @Test
    void shouldCancelOrder() throws Exception {
        Step02CancelOrder step02CancelOrder = new Step02CancelOrder();
        Order order = new Order();
        order.setOrderStatus(OrderStatus.AWAITING_CONFIRMATION);
        OrderCancelRow orderCancelRow = new OrderCancelRow();
        orderCancelRow.setOrder(order);

        OrderCancelRow resultRow = step02CancelOrder.process(orderCancelRow);

        assertEquals(OrderStatus.CANCELLED, resultRow.getOrder().getOrderStatus());
        assertEquals(Boolean.TRUE, resultRow.getProcessed());
    }
}

