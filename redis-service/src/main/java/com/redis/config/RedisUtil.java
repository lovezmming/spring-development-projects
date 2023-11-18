package com.redis.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {
    private static String EMPTY_JSON = "{}";
    private static String PRODUCT_ID_LOCK = "PRODUCT_ID_LOCK:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private Random random;

//    @Autowired
//    private Redisson redisson;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, gson.toJson(value));
    }

    public void setEmpty(String key) {
        redisTemplate.opsForValue().set(key, EMPTY_JSON, getEmptyTimeout(), TimeUnit.SECONDS);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, getRandomTimeout(timeout, unit), unit);
    }

    public void setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    public <T> T get(String key, Class<?> clazz) {
        String value = get(key);
        if (StringUtils.isEmpty(value) || value.equals(EMPTY_JSON)) {
            return null;
        }
        return (T) gson.fromJson(get(key), clazz);
    }

    public String get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            return EMPTY_JSON;
        }
        return (String) value;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    private int getRandomTimeout(long timeout, TimeUnit unit) {
        if (unit == TimeUnit.SECONDS) {
            return Long.valueOf(timeout).intValue() + random.nextInt(30) * 60;
        }
        return Long.valueOf(timeout).intValue();
    }

    private int getEmptyTimeout() {
        return 60 + random.nextInt(30);
    }
}
