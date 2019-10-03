package com.transporter.trip;

import com.transporter.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "T_TRIP")
public class Trip {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User customer;

}
