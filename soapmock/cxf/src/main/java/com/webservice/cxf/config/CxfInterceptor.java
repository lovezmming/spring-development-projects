package com.webservice.cxf.config;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class CxfInterceptor extends AbstractPhaseInterceptor<Message> {

    public CxfInterceptor() {
        super(Phase.RECEIVE);// 接收时处理
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        InputStream inputStream = message.getContent(InputStream.class);
        try {
            if (inputStream != null) {
                HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
                String stringBuffer = request.getRequestURL().toString();
                if (stringBuffer.contains("services")) {
                    String requestStr = IOUtils.toString(inputStream);
                    if (requestStr.contains("<soap:Body/>")) {
                        requestStr = requestStr.replace("<soap:Body/>",
                                "<soap:Body> <ns2: helloNoRequest xmlns:ns2=\"http://service.ws.webservice.com\"/> </soap:Body>");
                    }
                    message.setContent(InputStream.class, new ByteArrayInputStream(requestStr.getBytes()));
                }
            }
        } catch (Exception e) {

        }
    }
}