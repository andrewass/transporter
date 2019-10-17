package com.transporter.batch.ordercancel;

import com.transporter.order.Order;
import com.transporter.order.OrderRepository;
import com.transporter.order.OrderStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

public class Step01FillTable implements Tasklet {

    private OrderRepository orderRepository;

    Step01FillTable(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<OrderStatus> excludedOrders = List.of(OrderStatus.COMPLETE, OrderStatus.CANCELLED);
        List<Order> orders = orderRepository.findByOrderStatusNotInAndOrderDateBefore(excludedOrders, LocalDate.now());
        return null;
     }
}
