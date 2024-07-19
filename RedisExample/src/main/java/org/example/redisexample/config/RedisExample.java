package org.example.redisexample.config;

import org.example.redisexample.entity.Product;
import org.example.redisexample.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisExample {
    @SuppressWarnings("unused")
    private final ProductRepository productRepository;
    
    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            // System.out.println("Saving products...");
            // Product product1 = productRepository.save(Product.builder().name("Product 1").price(100).build());
            // Product product2 = productRepository.save(Product.builder().name("Product 2").price(200).build());
            // System.out.println("Products saved: " + product1 + ", " + product2);
            productRepository.save(Product.builder().name("Product 1").price(100).build());
            productRepository.save(Product.builder().name("Product 2").price(200).build());
        };
    }
}
