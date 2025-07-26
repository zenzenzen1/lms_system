package com.example.schedule_service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.example.schedule_service.base.BaseContainerTest;

class ScheduleServiceApplicationTests extends BaseContainerTest {
    @Container @ServiceConnection
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("sql/initdb_test.sql")
    ;
    @Autowired private RedisTemplate<String, Object> redisTemplate;
    // @Container
    // static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));

    // @SuppressWarnings("resource")
    // @Container
    // static GenericContainer<?> redis = new GenericContainer<>("redis:7.2")
    //         .withExposedPorts(6379)
    //         ;

    // @DynamicPropertySource
    // static void configure(DynamicPropertyRegistry registry) {

    //     registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

    //     // registry.add("spring.data.redis.host", () -> redis.getHost());
    //     // registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    //     registry.add("app.redis.redis-host", redis::getHost);
    //     registry.add("app.redis.redis-port", () -> String.valueOf(redis.getMappedPort(6379)));
    // }

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("test-key", "test-value");
        var value = redisTemplate.opsForValue().get("test-key");
        assertEquals("test-value", value);
    }

}
