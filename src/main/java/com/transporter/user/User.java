package com.transporter.user;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue
    Long id;


}
