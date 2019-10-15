package com.transporter.batch.ordercancel;

import com.transporter.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "T_B001_WORK_TABLE")
public class B001WorkTable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @MapsId
    private Order order;

    @Column(name = "NOTIFY_USER")
    private Boolean notifyUser;



}
