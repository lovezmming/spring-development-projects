package com.shev.compilation.common.support.cache;

import com.shev.compilation.common.support.web.JsonReturn;
import com.shev.compilation.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheManagerController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/getCacheDataByKey")
    public JsonReturn<Object> getCacheDataByKey(String name, String key)
    {
        logger.info("CacheManagerController getCacheDataByKey");
        Object object = cacheManager.getCache(name).get(key, Object.class);
        return new JsonReturn<>(object);
    }

}
