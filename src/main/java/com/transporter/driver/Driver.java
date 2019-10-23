package com.transporter.driver;

import com.transporter.order.Order;
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

    private String username;

    private Boolean notifyDriver = true;

    @OneToMany(mappedBy = "driver")
    private List<Order> orderList;


}
