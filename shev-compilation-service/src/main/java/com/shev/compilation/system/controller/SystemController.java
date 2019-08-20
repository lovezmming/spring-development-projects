package com.shev.compilation.system.controller;

import com.shev.compilation.common.support.WebBasicController;
import com.shev.compilation.common.support.web.JsonReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController extends WebBasicController
{

    @GetMapping("/getSystemTime")
    public JsonReturn<Long> getSystemTime()
    {
        return new JsonReturn<>(System.currentTimeMillis());
    }

}
