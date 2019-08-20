package com.shev.compilation.edumeta.cache.service;

import com.shev.compilation.common.Enum.CacheKeyEnum;

public interface EdumetaCacheService
{
    /**
     * name:edumeta
     * key:objectname:tenantId:id
     * value:object
     */
    void refreshEdumetaCache(CacheKeyEnum cacheKey);

    /**
     * name:edumeta
     * key:objectname:tenantId:id
     * value:object
     */
    void refreshEdumetaCache(String cacheKey, Object object);

    /**
     * name:edumeta
     * key:objectname:tenantId:id
     */
    void removeEdumetaCache(String cacheKey);

    /**
     * name:edumeta
     * key:objectname:tenantId:id
     * value:object
     */
    void refreshSpecialEdumetaCache(CacheKeyEnum cacheKey, Object object);
}
