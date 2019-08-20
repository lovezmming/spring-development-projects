package com.shev.compilation.user.cache.service;

import com.shev.compilation.user.entity.User;

public interface UserCacheService
{

    /**
     * 刷新所有用户权限缓存
     * name:account
     * key:permission:tenantId:userId
     * value:permission url
     */
    void refreshUserPermissionsCache();

    /**
     * 刷新所有用户姓名缓存
     * name:account
     * key:user:tenantId:userId
     * value:name
     */
    void refreshUserNamesCache();

    /**
     * 刷新所有暴露请求路径缓存
     * name:account
     * key:permission:urls
     * value:permission url
     */
    void refreshExposePermissionsCache();

    /**
     * 刷新指定用户缓存
     * name:account
     * key:user:tenantId:userId  permission:urls
     */
    void refreshUserCache(User user);

    /**
     * 移除指定用户缓存
     * name:account
     * key:user:tenantId:userId  permission:urls
     */
    void removeUserCache(User user);

    /**
     * 新增用户缓存
     * name:account
     * key:user:tenantId:userId  permission:urls
     */
    void createUserCache(User user);

    /**
     * 刷新指定职务权限缓存
     * name:account
     * key:user:tenantId:userId  permission:urls
     */
    void refreshDutyPermissionCache(String id);
}
