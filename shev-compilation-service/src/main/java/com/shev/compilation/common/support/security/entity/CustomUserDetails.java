package com.shev.compilation.common.support.security.entity;

import com.shev.compilation.user.entity.UserLogin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomUserDetails implements UserDetails
{

    private Collection<? extends GrantedAuthority> authorities;

    private String username;

    private String password;

    private String userId;

    private String id;

    private String tenantId;

    public CustomUserDetails()
    {}

    public CustomUserDetails(UserLogin userLogin)
    {
        if (userLogin != null)
        {
            id = userLogin.getId();
            tenantId = userLogin.getTenantId();
            userId = userLogin.getUserId();
            username = userLogin.getUserName();
            password = userLogin.getPassword();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities)
    {
        this.authorities = authorities;
    }

    public String getId()
    {
        return id;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public String getUserId()
    {
        return userId;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "username:" + this.username + ",password:" + this.password + ",userId:" + this.userId + ",permissionUrl:" + this.authorities;
    }
}
