package com.shev.itembank.common.cache.controller;

import com.shev.itembank.common.base.web.WebBasicController;
import com.shev.itembank.common.cache.request.CacheRequest;
import com.shev.itembank.common.redis.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
@Api(tags = "缓存管理接口")
public class CacheController extends WebBasicController
{
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private CacheService cacheService;

    @PostMapping("/delCache")
    @ApiOperation("缓存删除接口")
    public void delCache(@RequestBody CacheRequest request)
    {
        cacheService.del(request.getCacheKey());
        logger.info("del key:{}", request.getCacheKey());
    }

    @PostMapping("/setCache")
    @ApiOperation("缓存设置接口")
    public void setCache(@RequestBody CacheRequest request)
    {
        cacheService.set(request.getCacheKey(), request.getCacheValue());
        logger.info("put key:{},value:{}", request.getCacheKey(), request.getCacheValue());
    }

    @GetMapping("/getCache")
    @ApiOperation("缓存查询接口")
    public Object getCache(CacheRequest request)
    {
        Object value= cacheService.get(request.getCacheKey());
        logger.info("get key:{},value:{}", request.getCacheKey(), value);
        return value;
    }

}
