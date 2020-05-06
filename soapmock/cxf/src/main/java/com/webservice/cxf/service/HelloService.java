package com.webservice.cxf.service;

import com.webservice.cxf.request.HelloRequest;
import com.webservice.cxf.response.HelloResponse;
import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.stereotype.Service;
import org.apache.cxf.interceptor.InInterceptors;
import javax.jws.WebService;
import javax.xml.ws.soap.SOAPBinding;

@Service
@WebService(
        targetNamespace = "http://service.hello.com",
        endpointInterface = "com.hello.service.IMockRseService",
        serviceName = "IHelloService"
)
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@InInterceptors(interceptors = "com.hello.SpecInterceptor")
public class HelloService implements IHelloService {
    @Override
    public HelloResponse hello(HelloRequest helloRequest) {
        return new HelloResponse();
    }
}
