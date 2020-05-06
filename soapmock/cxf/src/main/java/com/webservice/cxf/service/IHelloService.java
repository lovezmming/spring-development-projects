package com.webservice.cxf.service;

import com.webservice.cxf.request.HelloRequest;
import com.webservice.cxf.response.HelloResponse;
import org.apache.cxf.interceptor.InInterceptors;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebResult;

@WebService(targetNamespace = "http://service.ws.webservice.com", name = "IHelloService")
public interface IHelloService {
    @WebMethod
    @WebResult
    public HelloResponse hello(@WebParam(name = "helloRequest") HelloRequest helloRequest);
}
