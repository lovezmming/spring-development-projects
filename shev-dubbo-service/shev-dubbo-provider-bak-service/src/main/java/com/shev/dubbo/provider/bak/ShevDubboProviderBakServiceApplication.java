package com.shev.dubbo.provider.bak;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@EnableDubboConfig
@SpringBootApplication
public class ShevDubboProviderBakServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ShevDubboProviderBakServiceApplication.class, args);
    }

}
