package com.shev.compilation.common.support.valid;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.exception.ValidationException;
import com.shev.compilation.common.support.HttpServletBaseRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RequestValidationAspect
{
    @Value("${request.valid}")
    private String valid;

    @Value("${request.timePeriod}")
    private String timePeriod;

    @Autowired
    private CacheManager cacheManager;

    @Before("@annotation(RequestValidate)")
    public void checkBeforeRequest(JoinPoint point) throws ValidationException
    {
        if ("1".equals(valid))
        {
            Object[] args = point.getArgs();
            if (args != null && args.length > 0 && (args[0] instanceof HttpServletBaseRequest))
            {
                HttpServletBaseRequest request = (HttpServletBaseRequest) args[0];
                Cache cache = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication instanceof AnonymousAuthenticationToken))
                {
                    String userinfo = cache.get(CacheKeyEnum.TOKEN.getKey() + authentication.getPrincipal(), String.class);
                    String[] userinfos = userinfo.split(",");
                    request.setCurrentUserTenantId(userinfos[0]);
                    request.setCurrentUserId(userinfos[1]);
                }
                RequestValidate requestValidate = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(RequestValidate.class);
                if (requestValidate.timestampValidate())
                {
                    long diff = Math.abs(System.currentTimeMillis() - request.getTimestamp());
                    if (diff > (Integer.valueOf(timePeriod) * 1000))
                        throw new ValidationException("Invalid.timestamp", "timestamp invalid");
                }
                if (request.getStart() == null)
                {
                    request.setStart(0);
                }
                if (request.getPageSize() == null)
                {
                    request.setPageSize(20);
                }
            }
        }
    }

}
