package com.example.schedule_service.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseContainerTest {
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));

    @SuppressWarnings("resource")
    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7.2")
            .withExposedPorts(6379)
            ;
     static {
        redis.start();
        kafka.start();
    }

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {

        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

        // registry.add("spring.data.redis.host", () -> redis.getHost());
        // registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
        registry.add("app.redis.redis-host", redis::getHost);
        registry.add("app.redis.redis-port", () -> String.valueOf(redis.getMappedPort(6379)));
    }
}
