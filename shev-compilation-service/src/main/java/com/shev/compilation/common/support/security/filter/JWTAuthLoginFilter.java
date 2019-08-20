package com.shev.compilation.common.support.security.filter;

import com.shev.compilation.common.Enum.CacheKeyEnum;
import com.shev.compilation.common.Enum.CacheNameEnum;
import com.shev.compilation.common.support.security.entity.CustomUserDetails;
import com.shev.compilation.common.util.JwtTokenUtil;
import com.shev.compilation.common.util.SpringApplicationUtil;
import com.shev.compilation.user.task.UserAsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JWTAuthLoginFilter extends UsernamePasswordAuthenticationFilter
{

    @Autowired
    private UserAsyncTask asyncTask;

    private AuthenticationManager authenticationManager;

    public JWTAuthLoginFilter()
    {}

    public JWTAuthLoginFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // UserLogin userLogin = new ObjectMapper().readValue(request.getInputStream(), UserLogin.class);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        CacheManager cacheManager = SpringApplicationUtil.getBean(CacheManager.class);
        List<String> permissionUrls = cacheManager.getCache(CacheNameEnum.ACCOUNT.getName()).get(CacheKeyEnum.PERMISSION.getKey() + customUserDetails.getUserId(), ArrayList.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permissionUrl : permissionUrls)
        {
            authorities.add(new SimpleGrantedAuthority(permissionUrl));
        }
        customUserDetails.setAuthorities(authorities);
        String token = JwtTokenUtil.generateAccessToken(customUserDetails);
        response.setHeader("token", JwtTokenUtil.tokenPrefix + token);
        if (asyncTask == null)
        {
            asyncTask = SpringApplicationUtil.getBean(UserAsyncTask.class);
        }
        asyncTask.updateUserLoginInfo(customUserDetails, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
    {
        response.getWriter().write("权限认证失败: " + failed.getMessage());
    }

}

