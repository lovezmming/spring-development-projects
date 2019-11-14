package com.shev.itembank.common.base.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig
{

    @Value("${connect.timeout}")
    private String connectTimeout;

    @Value("${read.timeout}")
    private String readTimeout;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory)
    {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory()
    {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Integer.valueOf(connectTimeout));
        factory.setReadTimeout(Integer.valueOf(readTimeout));
        return factory;
    }

}
