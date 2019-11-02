package com.transporter.entities.user;

import com.transporter.entities.order.Order;
import com.transporter.entities.user.inbox.UserInbox;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Column(name = "NOTIFY_USER")
    private Boolean notifyUser = true;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

}
