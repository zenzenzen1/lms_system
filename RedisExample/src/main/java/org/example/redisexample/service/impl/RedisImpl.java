package org.example.redisexample.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.example.redisexample.service.RedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisImpl implements RedisService{
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    
    public RedisImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }
    
    @Override
    public void delele(String key, List<String> fields) {
        fields.forEach(field -> hashOperations.delete(key, field));
    }
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    @Override
    public void delete(String key, String field) {
        hashOperations.delete(key, field);
    }
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    @Override
    public Map<String, Object> getField(String key) {
        return hashOperations.entries(key);
    }
    @Override
    public Set<String> getFieldPrefixes(String key) {
        return hashOperations.entries(key).keySet();
    }
    @Override
    public boolean hashExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }
    @Override
    public Object hashGet(String key, String field) {
        return hashOperations.get(key, field);
    }
    @Override
    public List<Object> hashGetByFieldPrefix(String key, String fieldPrefix) {
        List<Object> values = new ArrayList<>();
        Map<String, Object> entries = hashOperations.entries(key);
        entries.forEach((k, v) -> {
            if (k.startsWith(fieldPrefix)) {
                values.add(v);
            }
        });
        return values;
    }
    @Override
    public void hashSet(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }
    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    @Override
    public void setTimeToLive(String key, long timeoutInDays) {
        redisTemplate.expire(key, timeoutInDays, TimeUnit.DAYS);
    }
    
}
