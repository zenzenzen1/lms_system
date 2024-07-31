package com.example.lms_notification_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class Common {
    @Bean
    JsonMessageConverter jsonMessageConverter() {
        // byte stream to json
        return new JsonMessageConverter();
    }
}
