package com.transporter.batch.ordercancel;

import com.transporter.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ORDER_CANCEL_ROW")
class OrderCancelRow {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @MapsId
    private Order order;

    @Column(name = "NOTIFY_USER")
    private Boolean notifyUser;
}
