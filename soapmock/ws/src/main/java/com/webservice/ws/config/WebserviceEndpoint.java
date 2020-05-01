package com.webservice.ws.config;

import com.webservice.ws.service.HelloRequest;
import com.webservice.ws.service.HelloResp;
import com.webservice.ws.service.HelloResponse;
import com.webservice.ws.service.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebserviceEndpoint {
    private static Logger logger = LoggerFactory.getLogger(WebserviceEndpoint.class);

    private static final String NAMESPACE_URI = "http://service.ws.webservice.com";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "HelloRequest")
    @ResponsePayload
    public HelloResponse hello(@RequestPayload HelloRequest helloRequest) {
        logger.info("helloRequest:{}",
                helloRequest.getHelloReq() == null ? null : helloRequest.getHelloReq().getUsername());
        HelloResponse response = new HelloResponse();
        HelloResp resp = new HelloResp();
        resp.setResult(0);
        response.setHelloResp(resp);
        return new HelloResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "helloNoRequest")
    @ResponsePayload
    public HelloResponse helloNoRequest() {
        logger.info("helloNoRequest!");
        HelloResponse response = new HelloResponse();
        HelloResp resp = new HelloResp();
        resp.setResult(1);
        response.setHelloResp(resp);
        return new HelloResponse();
    }
}
