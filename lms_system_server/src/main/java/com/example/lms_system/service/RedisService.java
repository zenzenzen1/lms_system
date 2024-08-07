package com.example.lms_system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void delele(String key, List<String> fields) {
        fields.forEach(field -> hashOperations.delete(key, field));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(String key, String field) {
        hashOperations.delete(key, field);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Map<String, Object> getField(String key) {
        return hashOperations.entries(key);
    }

    public Set<String> getFieldPrefixes(String key) {
        return hashOperations.entries(key).keySet();
    }

    public boolean hashExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    public Object hashGet(String key, String field) {
        return hashOperations.get(key, field);
    }

    // public List<Object> hashGetByFieldPrefix(String key, String fieldPrefix) {
    //     // List<Object> values = new ArrayList
    //     Map<String, Object> entries = hashOperations.entries(key);
    //     entries.forEach((k, v) -> {
    //         if (k.startsWith(fieldPrefix)) {
    //             values.add(v);
    //         }
    //     });
    //     return values;
    // }

    public void hashSet(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }

    public void set(String key, Object value, long timeInSeconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.opsForValue().set(key, value, timeInSeconds, TimeUnit.SECONDS);
    }

    public void setTimeToLive(String key, long timeoutInDays) {
        redisTemplate.expire(key, timeoutInDays, TimeUnit.DAYS);
    }
}
