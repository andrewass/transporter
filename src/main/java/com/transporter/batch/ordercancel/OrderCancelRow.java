package com.transporter.batch.ordercancel;

import com.transporter.entities.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "T_ORDER_CANCEL_ROW")
class OrderCancelRow {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private Boolean notifyUser;

    private Boolean notifyDriver;

    private Boolean processed = false;
}
