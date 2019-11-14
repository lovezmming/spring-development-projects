package com.shev.itembank.system.controller;

import com.shev.itembank.common.base.valid.RequestValidate;
import com.shev.itembank.common.base.web.WebBasicController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/system")
@Api(tags = "系统管理接口")
public class SystemController extends WebBasicController
{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${latex.test}")
    private String latexUrl;

    @RequestValidate
    @GetMapping("/getSystemTime")
    @ResponseBody
    @ApiOperation("获取系统时间")
    public long getSystemTime()
    {
        return System.currentTimeMillis();
    }

}
