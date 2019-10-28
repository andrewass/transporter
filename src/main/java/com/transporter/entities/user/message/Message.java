package com.transporter.entities.user.message;

import com.transporter.entities.user.User;
import com.transporter.entities.user.inbox.UserInbox;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "T_MESSAGE")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_INBOX_ID")
    private UserInbox userInbox;

    private String message;

    private boolean isRead;

    @OneToOne
    @JoinColumn(name = "SENDER_ID")
    private User sender;
}
