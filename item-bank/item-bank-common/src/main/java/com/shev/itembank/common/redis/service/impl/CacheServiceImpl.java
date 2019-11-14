package com.shev.itembank.common.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.shev.itembank.common.redis.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService
{

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public Boolean set(String key, Object value)
    {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    @Override
    public Boolean set(String key, Object value, Integer period)
    {
        redisTemplate.opsForValue().set(key, value, period);
        return true;
    }

    @Override
    public Object get(String key)
    {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void del(String key)
    {
        redisTemplate.delete(key);
    }
}
