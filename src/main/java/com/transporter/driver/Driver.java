package com.transporter.driver;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "T_DRIVER")
public class Driver {

    @Id
    @GeneratedValue
    Long id;



}
