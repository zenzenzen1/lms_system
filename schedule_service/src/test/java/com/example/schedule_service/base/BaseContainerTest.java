package com.example.schedule_service.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseContainerTest {
}
