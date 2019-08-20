package com.shev.compilation.common.support.security.auth;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource
{
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException
    {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if ("/login".equals(requestUrl))
        {
            return null;
        }
        // 比对是否在允许访问的请求路径之内
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        List<String> permissionUrls = cache.get(CacheKeyEnum.PERMISSION.getKey() + "urls", ArrayList.class);
        if (!TextUtil.isEmpty(permissionUrls))
        {
            if (requestUrl.contains("?"))
            {
                requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("?"));
            }
            for (String permissionUrl : permissionUrls)
            {
                if (antPathMatcher.match(permissionUrl, requestUrl))
                {
                    return SecurityConfig.createList(requestUrl);
                }
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}