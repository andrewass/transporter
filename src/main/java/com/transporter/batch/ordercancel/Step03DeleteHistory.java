package com.transporter.batch.ordercancel;

import com.transporter.entities.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class Step03DeleteHistory implements Tasklet {

    private OrderCancelRowRepository orderCancelRowRepository;

    Step03DeleteHistory(OrderCancelRowRepository orderCancelRowRepository){
        this.orderCancelRowRepository = orderCancelRowRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(30);
        List<OrderCancelRow> ordersToDelete = orderCancelRowRepository.findHistoricRows(cutOffDate);
        log.info("STEP 3 - Delete History : Deleting "+ordersToDelete.size()+" rows from T_ORDER_CANCEL_ROW");
        orderCancelRowRepository.deleteAll(ordersToDelete);
        return RepeatStatus.FINISHED;
    }
}
