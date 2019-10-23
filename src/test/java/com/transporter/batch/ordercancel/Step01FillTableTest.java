package com.transporter.batch.ordercancel;

import com.transporter.driver.Driver;
import com.transporter.order.Order;
import com.transporter.order.OrderRepository;
import com.transporter.order.OrderStatus;
import com.transporter.tablemanagement.OrdersBuilder;
import com.transporter.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.transporter.order.OrderStatus.*;
import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class Step01FillTableTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderCancelRowRepository orderCancelRowRepository;

    @InjectMocks
    private Step01FillTable step01FillTable;

    @Mock
    StepContribution stepContribution;

    @Mock
    ChunkContext chunkContext;

    @Test
    void shouldReturnRepeatStatusFinished() throws Exception {
        when(orderRepository.findOrdersToCancel(any(List.class), any(LocalDateTime.class)))
                .thenReturn(getOrdersToCancel());
        RepeatStatus repeatStatus = step01FillTable.execute(stepContribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, repeatStatus);
    }

    private List<OrderStatus> getExcludedOrderStatus() {
        return List.of(CANCELLED, COMPLETE, TRIP_ACTIVATED);
    }

    private List<Order> getOrdersToCancel() {
        Driver driver = new Driver();
        User user = new User();
        Order order = new Order();
        order.setUser(user);
        order.setDriver(driver);
        return List.of(order);
    }
}