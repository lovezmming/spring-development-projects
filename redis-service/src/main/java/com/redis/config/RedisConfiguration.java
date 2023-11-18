package com.redis.config;

import com.redis.config.client.RedisCacheImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@Configuration
public class RedisConfiguration {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean("redisCache")
    @Primary
    public RedisCacheImpl redisMainCache() {
        RedisCacheImpl redisCache = new RedisCacheImpl();
        redisCache.setRedisTemplate(stringRedisTemplate);
        return redisCache;
    }
}
