package com.shev.compilation.common.support;

import com.shev.compilation.common.util.TextUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebBasicController
{
    protected Long parseLong(HttpServletRequest request, String key, Long defaultValue)
    {
        try
        {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e)
        {
            return defaultValue;
        }
    }
    
    protected Integer parseInteger(HttpServletRequest request, String key, Integer defaultValue)
    {
        try
        {
            return Integer.valueOf(request.getParameter(key));
        } catch (Exception e)
        {
            return defaultValue;
        }
    }
    
    protected String parseString(HttpServletRequest request, String key, String defaultValue)
    {
        return TextUtil.isEmpty(request.getParameter(key)) ? defaultValue : request.getParameter(key);
    }
    
    protected BigDecimal parseBigDecimal(HttpServletRequest request, String key, BigDecimal defaultValue)
    {
        try
        {
            return new BigDecimal(request.getParameter(key));
        } catch (Exception e)
        {
            return defaultValue;
        }
    }
    
    protected Boolean parseBoolean(HttpServletRequest request, String key, Boolean defaultValue)
    {
        try
        {
            String value = request.getParameter(key);
            return TextUtil.isEmpty(value) ? defaultValue : ("1".equals(value) || "true".equals(value));
        } catch (Exception e)
        {
            return defaultValue;
        }
    }
    
    protected List<Integer> parseList(HttpServletRequest request, String key, String delimiter, Integer defaultValue)
    {
        List<Integer> result = new ArrayList<Integer>();
        try
        {
            String[] values = request.getParameter(key).split(delimiter);
            for (String value : values)
                result.add(Integer.valueOf(value));
        } catch (Exception e)
        {
            result.add(defaultValue);
        }
        return result;
    }
    
    protected List<String> parseList(HttpServletRequest request, String key, String delimiter, String defaultValue)
    {
        List<String> result = new ArrayList<String>();
        try
        {
            String[] values = request.getParameter(key).split(delimiter);
            for (String value : values)
                result.add(value);
        } catch (Exception e)
        {
            result.add(defaultValue);
        }
        return result;
    }
    
    protected Map<String, String> getHeaders(HttpServletRequest request, String overrideDomain)
    {
        Map<String, String> headers = new HashMap<String, String>();
        String url = request.getRequestURL().toString();
        if (!TextUtil.isEmpty(overrideDomain))
            url = url.replaceAll("127.0.0.1", overrideDomain);
        if (!TextUtil.isEmpty(overrideDomain))
            url = url.replace("0:0:0:0:0:0:0:1", overrideDomain);
        if (!TextUtil.isEmpty(overrideDomain))
            url = url.replace("::1", overrideDomain);
        headers.put("originalAPIUrl", url);
        headers.put("originalRequestMethod", request.getMethod());
        return headers;
    }
}
