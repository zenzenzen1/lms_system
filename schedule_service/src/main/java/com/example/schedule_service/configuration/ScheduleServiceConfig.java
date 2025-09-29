package com.example.schedule_service.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;

@Configuration
public class ScheduleServiceConfig {
    @Bean
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new ScheduleServiceInstanceSupplier("lms-identity-service");
    }
    
}

class ScheduleServiceInstanceSupplier implements ServiceInstanceListSupplier{
    private String serviceId;
    public ScheduleServiceInstanceSupplier(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays
                .asList(new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8081, false),
                        new DefaultServiceInstance(serviceId + "2", serviceId, "localhost", 8082, false)));
    }
    @Override
    public String getServiceId() {
        return serviceId;
    }
    
}