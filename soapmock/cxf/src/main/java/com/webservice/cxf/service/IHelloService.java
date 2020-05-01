package com.webservice.cxf.service;

import com.webservice.cxf.request.HelloRequest;
import com.webservice.cxf.response.HelloResponse;

@WebService(targetNamespace = "http://service.ws.webservice.com", name = "IHelloService")
public interface IHelloService {
    @WebMethod
    @WebResult
    public HelloResponse hello(@WebParam(name = "helloRequest") HelloRequest helloRequest);
}
