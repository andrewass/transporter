package com.transporter.batch.ordercancel;

import com.transporter.entities.order.Order;
import com.transporter.entities.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderCancelRowRepository extends JpaRepository<OrderCancelRow, Long> {

    @Query("select ocr from OrderCancelRow ocr where ocr.dateUpdated < :cutOffDate")
    List<OrderCancelRow> findHistoricRows(@Param("cutOffDate") LocalDateTime cutOffDate);

}
