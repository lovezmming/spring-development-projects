package com.shev.dubbo.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@EnableDubboConfig
@SpringBootApplication
public class ShevDubboConsumerServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ShevDubboConsumerServiceApplication.class, args);
    }

}
