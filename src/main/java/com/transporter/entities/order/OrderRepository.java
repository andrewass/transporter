package com.transporter.entities.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o FROM Order o where o.orderStatus not in :stateList and o.tripDate < :currentDate")
    List<Order> findOrdersToCancel(@Param("stateList") List<OrderStatus> stateList,
                                   @Param("currentDate") LocalDateTime currentDate);

}
