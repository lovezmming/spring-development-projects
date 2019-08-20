package com.shev.compilation.common.support.security.filter;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.exception.ValidationException;
import com.shev.compilation.common.util.JwtTokenUtil;
import com.shev.compilation.common.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenFilter implements HandlerInterceptor
{
    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod))
        {
            return true;
        }
        if (TextUtil.isEmpty(token))
        {
            throw new ValidationException("Invalid.Token", "Invalid Token");
        }
        String userId = JwtTokenUtil.getUserIdFromToken(token);
        if (TextUtil.isEmpty(userId))
        {
            throw new ValidationException("Invalid.user", "Invalid user");
        }
        Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
        String oldToken = cache.get(CacheKeyEnum.TOKEN.getKey() + userId, String.class);
        if (!TextUtil.isEmpty(oldToken) && !oldToken.equals(token))
        {
            throw new ValidationException("Invalid.Token", "Invalid Token");
        }
        return true;
    }
}
