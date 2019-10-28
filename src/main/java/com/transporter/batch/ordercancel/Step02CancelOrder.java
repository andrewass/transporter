package com.transporter.batch.ordercancel;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderStatus;
import com.transporter.entities.user.User;
import com.transporter.entities.user.message.Message;
import org.springframework.batch.item.ItemProcessor;

public class Step02CancelOrder implements ItemProcessor<OrderCancelRow, OrderCancelRow> {

    @Override
    public OrderCancelRow process(OrderCancelRow row) throws Exception {
        Order order = row.getOrder();
        order.setOrderStatus(OrderStatus.CANCELLED);
        if(row.getNotifyUser()){
            sendMessage(order.getUser(), order.getId());
        }
        if(row.getNotifyDriver()){
            sendMessage(order.getDriver().getUser(), order.getId());
        }
        row.setProcessed(true);
        return row;
    }

    private void sendMessage(User user, Long orderId){
        Message message = new Message();
        message.setMessage("Order "+orderId+" was cancelled");
    }
}
