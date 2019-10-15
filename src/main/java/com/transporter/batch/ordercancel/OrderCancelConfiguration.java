package com.transporter.batch.ordercancel;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;

@EnableBatchProcessing
public class OrderCancelConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("order-cancel-job")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1(){
        return  stepBuilderFactory.get("fill-table")
                .tasklet(new Step01FillTable())
                .build();
    }

    @Bean
    public Step step2(){
        return stepBuilderFactory.get("cancel-orders")
                .<OrderCancelRow, OrderCancelRow>chunk(100)
                .reader(customReader())
                .processor(orderProcessor())
                .writer(customWriter())
                .build();
    }

    @Bean
    public Step step3(){
        return  stepBuilderFactory.get("delete-history")
                .tasklet(new Step03DeleteHistory())
                .build();
    }

    @Bean
    public ItemReader<OrderCancelRow> customReader(){
        return new JpaPagingItemReader<>();
    }

    @Bean
    public ItemProcessor<OrderCancelRow, OrderCancelRow> orderProcessor(){
        return new OrderProcessor();
    }

    @Bean
    public ItemWriter<OrderCancelRow> customWriter(){
        return null;
    }
}
