package com.transporter.batch.ordercancel;

import com.transporter.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PreProcessTaskletTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PreProcessTasklet preProcessTasklet;

    @Test
    public void firstTest() {
        assertEquals(2,2);
    }

}
