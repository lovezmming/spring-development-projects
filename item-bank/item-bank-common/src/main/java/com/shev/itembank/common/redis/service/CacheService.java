package com.shev.itembank.common.redis.service;

public interface CacheService
{

    public Boolean set(String key, Object value);

    public Boolean set(String key, Object value, Integer period);

    public Object get(String key);

    public void del(String key);

}
