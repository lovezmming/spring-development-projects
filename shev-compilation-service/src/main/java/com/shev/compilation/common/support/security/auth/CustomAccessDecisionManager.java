package com.shev.compilation.common.support.security.auth;

import com.shev.compilation.common.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager
{

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException
    {
        if (TextUtil.isEmpty(configAttributes))
        {
            throw new AccessDeniedException("无权限访问！");
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext())
        {
            ConfigAttribute configAttribute = iterator.next();
            String currUserRole = configAttribute.getAttribute();
            for(GrantedAuthority grantedAuthority : authentication.getAuthorities())
            {
                log.info("request:" + currUserRole + " exist:" + grantedAuthority.getAuthority().trim());
                if (currUserRole.trim().equals(grantedAuthority.getAuthority().trim()))
                {
                    if ("ROLE_LOGIN".equals(currUserRole.toUpperCase().trim()))
                    {
                        if (authentication instanceof AnonymousAuthenticationToken)
                        {
                            throw new BadCredentialsException("未登录");
                        } else
                        {
                            return;
                        }
                    }
                    return;
                }
            }
        }
        throw new AccessDeniedException("无访问权限！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute)
    {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return true;
    }
}
