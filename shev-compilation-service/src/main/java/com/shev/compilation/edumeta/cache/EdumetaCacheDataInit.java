package com.shev.compilation.edumeta.cache;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.edumeta.cache.service.EdumetaCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EdumetaCacheDataInit implements CommandLineRunner
{
    private static final Logger logger =  LoggerFactory.getLogger(EdumetaCacheDataInit.class);

    @Autowired
    private EdumetaCacheService edumetaCacheService;

    @Override
    public void run(String... args) throws Exception
    {
        logger.info("Edumeta Cache Data Init!");

        for (CacheKeyEnum cacheKeyEnum : CacheKeyEnum.values())
        {
            if (CacheNameEnum.EDUMETA.getName().toUpperCase().equals(cacheKeyEnum.getName().toUpperCase()))
            {
                if (!CacheKeyEnum.SUBJECT.getKey().equals(cacheKeyEnum.getKey()))
                {
                    continue;
                }
                logger.info(cacheKeyEnum.getKey() + " Cache Data Init!");
                edumetaCacheService.refreshEdumetaCache(cacheKeyEnum);
            }
        }
    }

}
