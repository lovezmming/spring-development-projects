package com.shev.itembank.latex.controller;

import com.shev.itembank.resource.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shev.itembank.latex.request.LatexRequest;
import com.shev.itembank.latex.service.LatexService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/latex")
public class LatexController
{
    private static final Logger logger = LoggerFactory.getLogger(LatexController.class);

    @Autowired
    private LatexService latexService;

    @Autowired
    private UploadService uploadService;

    @PostMapping("/latex2Pic")
    public Map<String, Object> latex(@RequestBody LatexRequest request)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            result = latexService.latex2Pic(request.getTenantId(), request.getType(), request.getXmlContent());
        } catch (Exception e)
        {
            logger.error("latex exception:{}", e.getMessage());
        }
        return result;
    }

    @GetMapping("/getSystemTime")
    public long getSystemTime()
    {
        return System.currentTimeMillis();
    }

    @GetMapping("/test")
    public String test(String test)
    {
        return "latex:" + test;
    }


    @GetMapping("/getQiniu")
    public String getQiniu()
    {
        try
        {
            String url = uploadService.downLoadResource("000001", "000001/10000010000030000000000000000001/1572344374445", "057ef0b5-4a04-4566-aac6-c37cb0255342");
            return  url;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
