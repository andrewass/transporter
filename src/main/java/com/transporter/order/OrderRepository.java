package com.transporter.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatusNotInAndOrderDateBefore(List<OrderStatus> orderStatus, LocalDate currentDate);

}
