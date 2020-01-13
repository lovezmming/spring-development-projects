package com.shev.dubbo.provider.service.impl;

import com.shev.dubbo.provider.api.service.DubboProviderService;
import org.springframework.stereotype.Service;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class DubboProviderServiceImpl implements DubboProviderService
{
    @Override
    public String getProviderInfo(String request)
    {
        return request + ":provider";
    }
}
