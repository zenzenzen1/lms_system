package com.example.schedule_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class KafkaConfig {
    // @Bean
    // NewTopic scheduleTopic() {
    //     return new NewTopic("create-schedule", 1, (short) 1);
    // }
}
