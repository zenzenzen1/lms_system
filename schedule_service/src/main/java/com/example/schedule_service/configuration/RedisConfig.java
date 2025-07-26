package com.example.schedule_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.Jedis;

@Configuration
// @Profile("!test")
public class RedisConfig {

    @Value("${app.redis.redis-host}")
    private String redisHost;

    @Value("${app.redis.redis-port}")
    private String redisPort;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisPort));

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    Jedis jedis() {
        return new Jedis(redisHost, Integer.parseInt(redisPort));
    }

    // @Bean
    // ObjectMapper redisObjectMapper() {
    // ObjectMapper objectMapper = new ObjectMapper();
    // SimpleModule simpleModule = new SimpleModule();
    // simpleModule.addSerializer(LocalDateTime.class, new
    // LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
    // simpleModule.addDeserializer(
    // LocalDateTime.class, new
    // LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
    // simpleModule.addSerializer(LocalDate.class, new
    // LocalDateSerializer(dateTimeFormatter));
    // simpleModule.addDeserializer(LocalDate.class, new
    // LocalDateDeserializer(dateTimeFormatter));
    // objectMapper.registerModule(simpleModule);
    // return objectMapper;
    // }
    //
    @Bean
    CommandLineRunner redisCommandLineRunner(RedisTemplate<String, Object> redisTemplate) {
        return args -> {
            redisTemplate.opsForValue().set("redisTemplateTest", "redisTemplateTest");
            jedis().set("jedisTest", "jedisTest");
            redisTemplate.delete(redisTemplate.keys("*"));
        };
    }
}
