package utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static constants.LmsConstants.Reid.NAMESPACE_ACCOUNT;

public class RedisUtil {

    private static JedisPool pool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128); // maximum active connections
        poolConfig.setMaxIdle(128);
        try {
            pool = new JedisPool(
                    poolConfig, "localhost", 6379, 2000);
        }catch (Exception e){
            pool = null;
        }
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    // Method for getting string values
    public String getValue(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        }
    }

    // Method for getting list values
    public List<String> getList(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.lrange(key, 0, -1);
        }
    }

    public Long getExpire(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.ttl(key);
        }
    }

    // Method for setting string values
    public void setValueExpire(String key, String value) {
        try (Jedis jedis = getJedis()) {
            jedis.setex(key, 1800,value);
        }
    }

    public void setExpire(String key, long time) {
        try (Jedis jedis = getJedis()) {
            jedis.expire(key, time);
        }
    }

    // Method for adding value to a list
    public void addToList(String key, String... values) {
        try (Jedis jedis = getJedis()) {
            jedis.lpush(key, values);
        }
    }

    public static String generateKeyForRedis(String key) {
        return  NAMESPACE_ACCOUNT + "." + key;
    }
}