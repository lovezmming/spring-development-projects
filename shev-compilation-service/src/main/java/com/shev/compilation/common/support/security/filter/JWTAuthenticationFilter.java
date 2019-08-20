package com.shev.compilation.common.support.security.filter;

import com.shev.compilation.common.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter
{
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(JwtTokenUtil.tokenPrefix))
        {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader)
    {
        String token = tokenHeader.replace(JwtTokenUtil.tokenPrefix, "");
        String username = JwtTokenUtil.getUserNameFromToken(token);
        Collection<? extends GrantedAuthority> authorities = JwtTokenUtil.getAuthsFromToken(token);
        if (username != null)
        {
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return null;
    }

}

