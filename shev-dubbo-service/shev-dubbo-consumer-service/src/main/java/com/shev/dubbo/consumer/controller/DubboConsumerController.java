package com.shev.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shev.dubbo.provider.api.service.DubboProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class DubboConsumerController
{

    @Reference
    private DubboProviderService dubboProviderService;

    @GetMapping("/getPaperList")
    public String getPaperList(String request)
    {
        System.out.printf("request:" + request);
        String result = dubboProviderService.getProviderInfo(request);
        System.out.println(result);
        return result;
    }

}
