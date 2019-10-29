package com.transporter.entities.review;

import com.transporter.entities.driver.Driver;
import com.transporter.entities.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "T_DRIVER_REVIEW")
public class DriverReview {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @CreationTimestamp
    @Column(name = "DATE_CREATED")
    private LocalDateTime dateCreated;
}
