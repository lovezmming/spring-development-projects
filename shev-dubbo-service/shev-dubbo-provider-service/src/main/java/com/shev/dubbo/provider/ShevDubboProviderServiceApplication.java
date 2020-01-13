package com.shev.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ShevDubboProviderServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ShevDubboProviderServiceApplication.class, args);
    }

}
