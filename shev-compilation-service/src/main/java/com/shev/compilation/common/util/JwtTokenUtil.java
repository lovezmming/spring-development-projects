package com.shev.compilation.common.util;

import com.shev.compilation.common.support.security.entity.CustomUserDetails;
import com.shev.compilation.user.entity.UserLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class JwtTokenUtil
{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.expiration}")
    private Integer jwtExpireTime;

    @Value("${jwt.tokenPrefix}")
    private String jwtTokenPrefix;

    public static String secret;

    public static String authHeader;

    public static Integer tokenExpireTime;

    public static String tokenPrefix;

    @PostConstruct
    public void init()
    {
        secret = jwtSecret;
        authHeader = jwtHeader;
        tokenPrefix = jwtTokenPrefix;
        tokenExpireTime = jwtExpireTime;
    }

    public static String generateAccessToken(UserDetails userDetails)
    {
        CustomUserDetails customUserDetails = (CustomUserDetails)userDetails;
        Map<String, Object> map = new HashMap<>();
        map.put("claim", customUserDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(map)
                .setSubject(customUserDetails.getUsername())
                .setId(customUserDetails.getUserId())
                .setIssuer(authHeader)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static String getUserIdFromToken(String token)
    {
        Claims claims = getClaimsFromToken(token);
        return claims.getId();
    }

    public static String getUserNameFromToken(String token)
    {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public static Collection<? extends GrantedAuthority> getAuthsFromToken(String token)
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Claims claims = getClaimsFromToken(token);
        List<Map<String, String>> authoritieses = (List<Map<String, String>>) claims.get("claim");
        for (Map<String, String> authorityMap : authoritieses)
        {
            for (String key : authorityMap.keySet())
            {
                authorities.add(new SimpleGrantedAuthority(authorityMap.get(key)));
            }
        }
        return authorities;
    }

    public static Claims getClaimsFromToken(String token)
    {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e)
        {
            claims = null;
        }
        return claims;
    }

    public static void main(String[] args)
    {
        UserLogin userLogin = new UserLogin();
        userLogin.setId("123");
        userLogin.setUserId("222");
        userLogin.setUserName("aaaa");
        userLogin.setPassword("bbbb");
        CustomUserDetails customUserDetails = new CustomUserDetails(userLogin);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("/user"));
        authorities.add(new SimpleGrantedAuthority("/system"));
        customUserDetails.setAuthorities(authorities);
        Map<String, Object> map = new HashMap<>();
        map.put("claim", customUserDetails.getAuthorities());
        String token = Jwts.builder()
            .setClaims(map)
            .setSubject(customUserDetails.getUsername())
            .setId(customUserDetails.getUserId())
            .setIssuer("auth")
            .setExpiration(new Date(System.currentTimeMillis() + 3600 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS512, "comp")
            .compact();
        System.out.println(token);

        Claims claims = Jwts.parser()
                .setSigningKey("comp")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
        System.out.println(claims.getIssuer());
        List<Map<String, String>> authoritieses = (List<Map<String, String>>) claims.get("claim");
        for (Map<String, String> s : authoritieses)
            for (String ss : s.keySet())
                System.out.println(ss + "      " + s.get(ss));
    }
}
