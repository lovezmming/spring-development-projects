package com.shev.compilation.common.support.security;

import com.shev.compilation.common.support.security.auth.CustomAccessDecisionManager;
import com.shev.compilation.common.support.security.auth.CustomFilterInvocationSecurityMetadataSource;
import com.shev.compilation.common.support.security.entity.CustomPasswordEncoder;
import com.shev.compilation.common.support.security.filter.JWTAuthLoginFilter;
import com.shev.compilation.common.support.security.filter.JWTAuthenticationFilter;
import com.shev.compilation.common.support.security.handler.*;
import com.shev.compilation.common.support.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    private CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired
    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new CustomPasswordEncoder());
        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authenticationProvider(authenticationProvider())
                .httpBasic()
                .authenticationEntryPoint(customAuthenticationEntryPointHandler)

                .and()
                .authorizeRequests()
                .withObjectPostProcessor(
                        new ObjectPostProcessor<FilterSecurityInterceptor>()
                        {
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O o)
                            {
                                o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                                o.setAccessDecisionManager(customAccessDecisionManager);
                                return o;
                            }
                        })
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .permitAll()
                .failureHandler(customAuthenticationFailureHandler)
                .successHandler(customAuthenticationSuccessHandler)

                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)

                .and()
                .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilter(new JWTAuthLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));

        http.cors().disable();
        // 禁用csrf防御机制(跨域请求伪造)，在测试和开发会比较方便。
        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity web)
    {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(customPasswordEncoder);
        return authenticationProvider;
    }
}
