package com.transporter.batch.ordercancel;

import com.transporter.order.Order;
import com.transporter.order.OrderRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static com.transporter.order.OrderStatus.CANCELLED;
import static com.transporter.order.OrderStatus.COMPLETE;
import static java.util.Collections.singletonMap;

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

    @Bean
    public Job job() {
        return jobBuilderFactory.get("order-cancel-job")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("fill-table")
                .tasklet(new Step01FillTable(orderRepository))
                .build();
    }

    @Bean
    public Step step2() {
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

    /*
    @Bean
    public JobLauncher jobLauncher(){
        return new SimpleJobLauncher();
    }
*/
    @Bean
    public JpaPagingItemReader<OrderCancelRow> orderReader() {
        String jplQuery = "select o from Order o where o.orderStatus not in (:status_params)";
        JpaPagingItemReader<OrderCancelRow> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString(jplQuery);
        reader.setParameterValues(singletonMap("status_params", List.of(COMPLETE, CANCELLED)));

        return reader;
    }

    @Bean
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }

    @Bean
    public JpaItemWriter<OrderCancelRow> customWriter() {
        JpaItemWriter<OrderCancelRow> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
