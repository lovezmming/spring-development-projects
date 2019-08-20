package com.shev.compilation.edumeta.task;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.edumeta.cache.service.EdumetaCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EdumetaAsyncTask
{
    @Autowired
    private EdumetaCacheService edumetaCacheService;

    @Async
    public void refreshEdumetaCache(String cacheKey, Object object)
    {
        edumetaCacheService.refreshEdumetaCache(cacheKey, object);
    }

    @Async
    public void removeEdumetaCache(String cacheKey)
    {
        edumetaCacheService.removeEdumetaCache(cacheKey);
    }

    @Async
    public void refreshSpecialEdumetaCache(CacheKeyEnum cacheKey, Object object)
    {
        edumetaCacheService.refreshSpecialEdumetaCache(cacheKey, object);
    }

}
