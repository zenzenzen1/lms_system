package com.example.lms_notification_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class Common {
    @Bean
    JsonMessageConverter jsonMessageConverter() {
        // byte stream to json
        return new JsonMessageConverter();
    }
    
    @Bean
    DefaultErrorHandler defaultErrorHandler(KafkaOperations<String, Object> kafkaOperations) {
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaOperations), new FixedBackOff(1000, 2));
    }
}
