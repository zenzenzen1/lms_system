package com.example.kafka_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.event.dto.NotificationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaExampleApplication {
	
	@KafkaListener(topics = "create-user")
	public void listen(NotificationEvent notificationEvent){
		log.info("Message received: " + notificationEvent);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaExampleApplication.class, args);
	}
	
	// @Bean
	// NewTopic createProduct(){
	// 	return new NewTopic("create-product", 1, (short)1);
	// }

}
// @RestController
// @RequestMapping({"/products"})
// @Slf4j
// @RequiredArgsConstructor
// class ProductController{
	// private final KafkaTemplate<String, Object> kafkaTemplate;
	
	// @KafkaHandler
	// public void errorHandler(Exception exception){
	// 	log.error("Error: " + exception.getMessage());
	// }
	
	
	// @KafkaListener(topics = {"create-product"})
	// public void listen(Object product){
	// 	log.info("Product received: " + product);
	// }
	
	// @KafkaHandler()
	// public void defaultHandler(Object object){
	// 	log.info("Object received: " + object);
	// }
	
	// @PostMapping("")
	// public Product postMethodName(@RequestBody Product product) {
	// 	kafkaTemplate.send("create-product", product);
	// 	kafkaTemplate.setMessageConverter(new ProductMessageConvertor());
	// 	return Product.builder().id(product.getId()).name(product.getName()).build();
	// }
// }

// @Component
// @KafkaListeners({@KafkaListener(id = "notification-group", topics = {"create-product", "create-user"})})
// @Slf4j
// class KafkaListenerDemo {
// 	@KafkaHandler
// 	public void insertProdcut(Product product){
// 		log.info("Product received: " + product);
// 	}
	
// 	@KafkaHandler(isDefault = true)
// 	public void defaultHandler(Object object){
// 		log.info("Object received: " + object);
// 	}
	
// 	@KafkaHandler
// 	public void errorHandler(Exception exception){
// 		log.error("Error: " + exception.getMessage());
// 	}
	
// 	@KafkaHandler
// 	public void listen(NotificationEvent notificationEvent){
// 		log.info("Message received: " + notificationEvent);
// 	}
// }

// @Configuration
// class KafkaConfig{	
// 	@Bean
// 	public NewTopic createProduct(){
// 		return new NewTopic("create-product", 1, (short) 1);
// 	}
// }