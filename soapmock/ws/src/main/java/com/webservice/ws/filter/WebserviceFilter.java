package com.webservice.ws.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class WebserviceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (!req.getRequestURI().contains("rse")) {
            chain.doFilter(request, response);
            return;
        }
        String contentType = req.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            chain.doFilter(request, response);
            return;
        }
        String webserviceAction =
                contentType.substring(contentType.lastIndexOf("urn:") + "urn:".length(), contentType.lastIndexOf("\""));
        if ("helloNoRequest".equalsIgnoreCase(webserviceAction)) {
            String requestBody = getRequestBody(req);
            // 未传body内容
            if (requestBody.contains("<soap:Body/>")) {
                requestBody = requestBody.replace("<soap:Body/>",
                        "<soap:Body> <ns2:helloRequest xmlns=\"http://xx\" xmlns:ns2=\"http://xx\"/> </soap:Body>");
            }
            WebserviceRequestWrapper requestWrapper =
                    new WebserviceRequestWrapper((HttpServletRequest) request, requestBody);
            chain.doFilter(requestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private String getRequestBody(HttpServletRequest req) throws IOException {
        String requestBody = null;
        try {
            BufferedReader br = req.getReader();
            StringBuffer reqBody = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                reqBody.append(str).append("\r\n");
            }
            requestBody = reqBody.toString();
        } catch (Exception e) {
            log.error("WebserviceFilter getRequestBody error!");
        }
        return requestBody;
    }
}