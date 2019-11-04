package com.transporter.batch.ordercancel;

import com.transporter.entities.order.Order;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "NOTIFY_USER")
    private Boolean notifyUser = false;

    @Column(name = "USER_NOTIFIED")
    private Boolean userNotified = false;

    @Column(name = "NOTIFY_DRIVER")
    private Boolean notifyDriver = false;

    @Column(name = "DRIVER_NOTIFIED")
    private Boolean driverNotified = false;

    private Boolean processed = false;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;

}
