package com.shev.compilation.user.task;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.support.security.entity.CustomUserDetails;
import com.shev.compilation.user.cache.service.UserCacheService;
import com.shev.compilation.user.dao.service.UserDaoService;
import com.shev.compilation.user.entity.User;
import com.shev.compilation.user.entity.UserLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserAsyncTask
{

    private static final Logger logger = LoggerFactory.getLogger(UserAsyncTask.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserCacheService userCacheService;

    @Async
    public void updateUserLoginInfo(CustomUserDetails customUserDetails, String token)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        cache.put(CacheKeyEnum.TOKEN.getKey() + customUserDetails.getUserId(), token);
        cache.put(CacheKeyEnum.TOKEN.getKey() + customUserDetails.getUsername(), customUserDetails.getTenantId() + "," + customUserDetails.getUserId());
        UserLogin userLogin = userDaoService.getUserLoginById(customUserDetails.getId());
        userLogin.setLastLoginTime(new Date());
        userDaoService.updateUserLogin(userLogin);
    }

    @Async
    public void refreshUserCache(User user)
    {
        userCacheService.refreshUserCache(user);
    }

    @Async
    public void removeUserCache(User user)
    {
        userCacheService.removeUserCache(user);
    }

    @Async
    public void createUserCache(User user)
    {
        userCacheService.createUserCache(user);
    }

    @Async
    public void refreshDutyPermissionCache(String id)
    {
        userCacheService.refreshDutyPermissionCache(id);
    }
}
