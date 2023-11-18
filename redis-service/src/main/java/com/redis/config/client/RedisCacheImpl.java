package com.redis.config.client;

import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Setter
public class RedisCacheImpl implements RedisCache {
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> _class) {
        String result = get(key);
        if (StringUtils.hasText(result)) {
            return gson.fromJson(result, _class);
        }
        return null;
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public void hSet(String key, String field, String value, long time, TimeUnit unit) {
        redisTemplate.opsForHash().put(key, field, value);
        redisTemplate.expire(key, time, unit);
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}
