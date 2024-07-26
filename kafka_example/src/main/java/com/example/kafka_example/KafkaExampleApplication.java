package com.example.kafka_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.event.dto.NotificationEvent;

import lombok.extern.log4j.Log4j2;


@SpringBootApplication
@Log4j2
public class KafkaExampleApplication {
	@KafkaListener(topics = {"create-user"})
	public void listen(NotificationEvent notificationEvent){
		log.info("Message received: " + notificationEvent);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaExampleApplication.class, args);
	}

}
