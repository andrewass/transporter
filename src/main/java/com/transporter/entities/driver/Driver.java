package com.transporter.entities.driver;

import com.transporter.entities.order.Order;
import com.transporter.entities.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "T_DRIVER")
public class Driver {

    @Id
    @GeneratedValue
    private Long id;

    private Boolean notifyDriver = true;

    @OneToMany(mappedBy = "driver")
    private List<Order> orderList;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    User user;

}
