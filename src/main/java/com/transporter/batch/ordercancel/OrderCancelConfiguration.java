package com.transporter.batch.ordercancel;

import com.transporter.entities.order.OrderRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class OrderCancelConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderCancelRowRepository orderCancelRowRepository;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("order-cancel-job")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("fill-table")
                .tasklet(new Step01FillTable(orderRepository, orderCancelRowRepository))
                .build();
    }

    @Bean
    public Step step2() throws Exception {
        return stepBuilderFactory.get("cancel-orders")
                .<OrderCancelRow, OrderCancelRow>chunk(100)
                .reader(orderReader())
                .processor(orderProcessor())
                .writer(customWriter())
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("delete-history")
                .tasklet(new Step03DeleteHistory())
                .build();
    }

    @Bean
    public JpaPagingItemReader<OrderCancelRow> orderReader() throws Exception {
        String jplQuery = "select o from OrderCancelRow o where o.processed = FALSE";
        JpaPagingItemReader<OrderCancelRow> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString(jplQuery);
        reader.setPageSize(100);
        reader.afterPropertiesSet();
        return reader;
    }

    @Bean
    public Step02CancelOrder orderProcessor() {
        return new Step02CancelOrder();
    }

    @Bean
    public JpaItemWriter<OrderCancelRow> customWriter() {
        JpaItemWriter<OrderCancelRow> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
