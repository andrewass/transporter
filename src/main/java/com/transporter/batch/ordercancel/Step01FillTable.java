package com.transporter.batch.ordercancel;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderRepository;
import com.transporter.entities.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Step01FillTable implements Tasklet {

    private OrderRepository orderRepository;
    private OrderCancelRowRepository orderCancelRowRepository;

    Step01FillTable(OrderRepository orderRepository, OrderCancelRowRepository orderCancelRowRepository) {
        this.orderRepository = orderRepository;
        this.orderCancelRowRepository = orderCancelRowRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<Order> ordersToCancel = findOrdersToCancel();
        log.info("STEP 1 - Fill table : Fetched " + ordersToCancel.size() + " orders to cancel");
        List<OrderCancelRow> rows = createOrderCancelRows(ordersToCancel);
        orderCancelRowRepository.saveAll(rows);
        return RepeatStatus.FINISHED;
    }

    private List<OrderCancelRow> createOrderCancelRows(List<Order> ordersToCancel) {
        List<OrderCancelRow> orderCancelRowList = new ArrayList<>();
        for (Order order : ordersToCancel) {
            OrderCancelRow orderCancelRow = new OrderCancelRow();
            orderCancelRow.setOrder(order);
            orderCancelRow.setNotifyUser(order.getUser().getNotifyUser());
            orderCancelRow.setNotifyDriver(order.getDriver().getNotifyDriver());
            orderCancelRowList.add(orderCancelRow);
        }
        return orderCancelRowList;
    }

    private List<Order> findOrdersToCancel() {
        List<OrderStatus> excludedOrders = List.of(OrderStatus.COMPLETE, OrderStatus.CANCELLED, OrderStatus.TRIP_ACTIVATED);
        return orderRepository.findOrdersToCancel(excludedOrders, LocalDateTime.now());
    }
}
