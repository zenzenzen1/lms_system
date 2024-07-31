package com.example.lms_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
// @EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.DIRECT)
public class LmsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsSystemApplication.class, args);
    }
}
