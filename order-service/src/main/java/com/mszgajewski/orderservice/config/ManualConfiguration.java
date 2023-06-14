package com.mszgajewski.orderservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.annotation.PostConstruct;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ManualConfiguration {

    private final KafkaTemplate kafkaTemplate;

    @PostConstruct
    void setup(){
        this.kafkaTemplate.setObservationEnabled(true);
    }

}
