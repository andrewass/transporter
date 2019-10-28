package com.transporter.entities.user.inbox;

import com.transporter.entities.user.message.Message;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "T_USER_INBOX")
public class UserInbox {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "userInbox")
    private List<Message> messageList;

}
