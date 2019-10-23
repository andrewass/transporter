package com.transporter.batch.ordercancel;

import com.transporter.order.Order;
import com.transporter.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

public class Step02CancelOrder implements ItemProcessor<OrderCancelRow, OrderCancelRow> {

    @Override
    public OrderCancelRow process(OrderCancelRow row) throws Exception {
        Order order = row.getOrder();
        order.setOrderStatus(OrderStatus.CANCELLED);
        return row;
    }
}
