package com.redis.config.client;

import java.util.concurrent.TimeUnit;

public interface RedisCache {
    void set(String key, String value);

    void set(String key, String value, long time, TimeUnit unit);

    String get(String key);

    <T> T get(String key, Class<T> _class);

    void remove(String key);

    Long getExpire(String key);

    Object hGet(String key, String field);

    void hSet(String key, String field, String value, long time, TimeUnit unit);

    Boolean exists(String key);
}
