package com.transporter.batch.ordercancel;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class Step03DeleteHistoryTest {

    @Mock
    OrderCancelRowRepository orderCancelRowRepository;

    @InjectMocks
    Step03DeleteHistory step03DeleteHistory;

    @Mock
    StepContribution stepContribution;

    @Mock
    ChunkContext chunkContext;

    @Test
    void shouldReturnRepeatStatusFinished() throws Exception {
        List<OrderCancelRow> ordersToDelete = createOrdersToDelete();
        when(orderCancelRowRepository.findHistoricRows(any(LocalDateTime.class))).thenReturn(ordersToDelete);
        RepeatStatus repeatStatus = step03DeleteHistory.execute(stepContribution, chunkContext);
        assertEquals(RepeatStatus.FINISHED, repeatStatus);
    }

    private List<OrderCancelRow> createOrdersToDelete() {
        return List.of(new OrderCancelRow(), new OrderCancelRow());
    }

}