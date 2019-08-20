package com.shev.compilation.user.cache.service.impl;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.util.TextUtil;
import com.shev.compilation.user.cache.UserCacheDataInit;
import com.shev.compilation.user.cache.service.UserCacheService;
import com.shev.compilation.user.dao.service.UserDaoService;
import com.shev.compilation.user.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCacheServiceImpl implements UserCacheService
{

    private static final Logger logger =  LoggerFactory.getLogger(UserCacheServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserDaoService userDaoService;

    @Override
    public void refreshUserPermissionsCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<UserLogin> userLogins = userDaoService.getUserLoginsByParams(null);
        if (!TextUtil.isEmpty(userLogins))
        {
            for (UserLogin userLogin : userLogins)
            {
                initUserPermission(cache, userLogin);
            }
        }
    }

    @Override
    public void refreshUserNamesCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        String key;
        Map<String, Object> params = new HashMap<>();
        List<User> users = userDaoService.getUsersByParams(params);
        if (!TextUtil.isEmpty(users))
        {
            for (User user : users)
            {
                initUserName(cache, user);
            }
        }
    }

    @Override
    public void refreshExposePermissionsCache()
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        String key;
        List<Permission> permissionList = userDaoService.getPermissions();
        if (!TextUtil.isEmpty(permissionList))
        {
            List<String> urls = new ArrayList<>();
            for (Permission permission : permissionList)
            {
                urls.add(permission.getUrl());
            }
            if (!TextUtil.isEmpty(urls))
            {
                key = CacheKeyEnum.PERMISSION.getKey() + "urls";
                cache.put(key, urls);
                logger.info("cachename:{},key:{},value:{}", CacheNameEnum.ACCOUNT.getName(), key, urls);
            }
        }
    }

    @Override
    public void refreshUserCache(User user)
    {
        removeUserCache(user);
        createUserCache(user);
    }

    @Override
    public void removeUserCache(User user)
    {
        String currKey = user.getTenantId() + ":" + user.getId();
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());

        // name:account  key:user:tenantId:userId  value:name
        cache.evict(CacheKeyEnum.USER.getKey() + currKey);

        //name:account  key:permission:tenantId:userId  value:permission url
        cache.evict(CacheKeyEnum.PERMISSION.getKey() + currKey);
    }

    @Override
    public void createUserCache(User user)
    {
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        // user name
        initUserName(cache, user);

        // user permission
        Map<String, Object> params = new HashMap<>();
        params.put("userId", user.getId());
        params.put("tenantId", user.getTenantId());
        List<UserLogin> userLogins = userDaoService.getUserLoginsByParams(params);
        initUserPermission(cache, userLogins.get(0));
    }

    @Override
    public void refreshDutyPermissionCache(String id)
    {
        List<UserDutyRelation> userDutyRelations = userDaoService.getUserDutyRelationsByDutyId(id);
        Map<String, Object> params;
        List<UserLogin> userLogins;
        if (!TextUtil.isEmpty(userDutyRelations))
        {
            Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
            for (UserDutyRelation userDutyRelation : userDutyRelations)
            {
                params = new HashMap<>();
                params.put("userId", userDutyRelation.getUserId());
                userLogins = userDaoService.getUserLoginsByParams(params);
                if (!TextUtil.isEmpty(userLogins))
                {
                    for (UserLogin userLogin : userLogins)
                    {
                        initUserPermission(cache, userLogin);
                    }
                }
            }
        }
    }

    private void initUserName(Cache cache, User user)
    {
        String key = CacheKeyEnum.USER.getKey() + user.getTenantId() + ":" + user.getId();
        cache.put(key, user.getName());
        logger.info("cachename:{},key:{},value:{}", CacheNameEnum.ACCOUNT.getName(), key, user.getName());
    }

    private void initUserPermission(Cache cache, UserLogin userLogin)
    {
        List<String> permissionUrls = new ArrayList<>();
        List<UserPermissionRelation> userPermissionRelations = userDaoService.getUserPermissionRelationsByUserId(userLogin.getUserId());
        Permission permission;
        if (!TextUtil.isEmpty(userPermissionRelations))
        {
            for (UserPermissionRelation userPermissionRelation : userPermissionRelations)
            {
                permission = userDaoService.getPermissionById(userPermissionRelation.getPermissionId());
                permissionUrls.add(permission.getUrl());
            }
        }
        List<UserDutyRelation> userDutyRelations = userDaoService.getUserDutyRelationsByUserId(userLogin.getUserId());
        if (!TextUtil.isEmpty(userDutyRelations))
        {
            for (UserDutyRelation userDutyRelation : userDutyRelations)
            {
                List<DutyPermissionRelation> dutyPermissionRelations = userDaoService.getDutyPermissionRelationsByDutyId(userDutyRelation.getDutyId());
                if (!TextUtil.isEmpty(dutyPermissionRelations))
                {
                    for (DutyPermissionRelation dutyPermissionRelation : dutyPermissionRelations)
                    {
                        permission = userDaoService.getPermissionById(dutyPermissionRelation.getPermissionId());
                        permissionUrls.add(permission.getUrl());
                    }
                }
            }
        }
        if (!TextUtil.isEmpty(permissionUrls))
        {
            String key = CacheKeyEnum.PERMISSION.getKey() + userLogin.getTenantId() + ":" + userLogin.getUserId();
            cache.put(key, permissionUrls);
            logger.info("cachename:{},key:{},value:{}", CacheNameEnum.ACCOUNT.getName(), key, permissionUrls);
        }
    }

}
