package com.transporter.batch.ordercancel;

import com.transporter.entities.driver.Driver;
import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderStatus;
import com.transporter.entities.user.User;
import com.transporter.entities.user.inbox.UserInbox;
import com.transporter.entities.user.inbox.UserInboxRepository;
import com.transporter.entities.user.message.Message;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class Step02CancelOrder implements ItemProcessor<OrderCancelRow, OrderCancelRow> {

    @Autowired
    UserInboxRepository userInboxRepository;

    @Override
    public OrderCancelRow process(OrderCancelRow row) throws Exception {
        Order order = row.getOrder();
        order.setOrderStatus(OrderStatus.CANCELLED);
        if(row.getNotifyUser()){
            sendMessageToUser(order.getUser(), order.getId());
            row.setUserNotified(true);
        }
        if(row.getNotifyDriver()){
            sendMessageToUser(order.getDriver().getUser(), order.getId());
            row.setDriverNotified(true);
        }
        row.setProcessed(true);
        return row;
    }

    private void sendMessageToUser(User user, Long orderId){
        Message message = new Message("Your customer order "+orderId+" was cancelled");
        sendMessage(user.getId(), message);
    }

    private void sendMessageToDriver(Driver driver, Long orderId){
        Message message = new Message("Your driver order "+orderId+" was cancelled");
        sendMessage(driver.getUser().getId(), message);
    }

    private void sendMessage(Long userId, Message message){
        UserInbox userInbox = userInboxRepository.findByUserId(userId);
        userInbox.addMessage(message);
        message.setUserInbox(userInbox);
        userInboxRepository.save(userInbox);
    }
}
