package com.shev.compilation.common.support.security.service;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.support.security.entity.CustomUserDetails;
import com.shev.compilation.common.util.TextUtil;
import com.shev.compilation.user.dao.custom.UserLoginCustomDao;
import com.shev.compilation.user.entity.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService
{

    @Autowired
    private UserLoginCustomDao userLoginCustomDao;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if (TextUtil.isEmpty(username))
        {
            throw new UsernameNotFoundException("Invalid username!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<UserLogin> userLogins = userLoginCustomDao.getUserLoginsByParams(params);
        if (TextUtil.isEmpty(userLogins))
        {
            log.info("用户:" + username + "不存在！");
            throw new UsernameNotFoundException("用户:" + username + "不存在！");
        }
        UserLogin userLogin = userLogins.get(0);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!TextUtil.isEmpty(userLogin.getUserId()))
        {
            Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
            List<String> permissionUrls = cache.get(CacheKeyEnum.PERMISSION.getKey() + userLogin.getTenantId() + ":" + userLogin.getUserId(), ArrayList.class);
            if (!TextUtil.isEmpty(permissionUrls))
            {
                for (String permissionUrl : permissionUrls)
                {
                    authorities.add(new SimpleGrantedAuthority(permissionUrl));
                }
            }
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(userLogin);
        customUserDetails.setAuthorities(authorities);
        log.info(customUserDetails.toString());
        return customUserDetails;
    }

}
