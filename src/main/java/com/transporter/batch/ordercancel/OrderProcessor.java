package com.transporter.batch.ordercancel;

import com.transporter.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class OrderProcessor implements ItemProcessor<OrderCancelRow, OrderCancelRow> {

    @Override
    public OrderCancelRow process(OrderCancelRow item) throws Exception {
        Order order = item.getOrder();
        return null;
    }
}
