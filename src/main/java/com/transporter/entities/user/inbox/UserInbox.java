package com.transporter.entities.user.inbox;

import com.transporter.entities.order.Order;
import com.transporter.entities.user.User;
import com.transporter.entities.user.message.Message;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "T_USER_INBOX")
public class UserInbox {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "userInbox", cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();

    public void addMessage(Message message){
        messageList.add(message);
    }
}
