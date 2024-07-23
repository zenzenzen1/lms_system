package org.example.redisexample.service;

import java.util.List;

import org.example.redisexample.entity.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;

    private String generateKey() {
        return "products";
    }

    public List<Product> getAllProduct() throws JsonMappingException, JsonProcessingException {
        String key = generateKey();
        String json = (String) redisTemplate.opsForValue().get(key);
        List<Product> products = json != null
                ? redisObjectMapper.readValue(json, new TypeReference<List<Product>>() { })
                : null;
        return products;
    }

    public void clear() {
        redisTemplate.delete(generateKey());
    }

    public void saveAllProducts(List<Product> products) throws JsonProcessingException {
        String key = generateKey();
        String json = redisObjectMapper.writeValueAsString(products);
        System.out.println(json);
        redisTemplate.opsForValue().set(key, json);
    }
}
