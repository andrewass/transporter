package com.transporter.trip;

import com.transporter.driver.Driver;
import com.transporter.order.Order;
import com.transporter.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "T_TRIP")
public class Trip {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private Integer price;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @CreationTimestamp
    private LocalDateTime dateUpdated;
}
