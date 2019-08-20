package com.shev.compilation.user.cache;

import com.shev.compilation.common.datasource.dynamic.DataSourceQuery;
import com.shev.compilation.user.cache.service.UserCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCacheDataInit implements CommandLineRunner
{
    private static final Logger logger =  LoggerFactory.getLogger(UserCacheDataInit.class);

    @Autowired
    private UserCacheService userCacheService;

    @Override
    @DataSourceQuery
    public void run(String... args) throws Exception
    {
        // name:account  key:user:tenantId:userId  value:name
        userCacheService.refreshUserNamesCache();

        //name:account  key:permission:tenantId:userId  value:permission url
        userCacheService.refreshUserPermissionsCache();

        //name:account  key:permission:urls  value:permission url
        userCacheService.refreshExposePermissionsCache();
    }

}
