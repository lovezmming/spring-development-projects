package com.webservice.cxf.config;

import com.webservice.cxf.service.IHelloService;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class CxfConfig {
    @Resource
    private Bus bus;

   /* public Bus getBus() {
    }*/

    @Resource
    private IHelloService iHelloService;

    @Bean
    public Endpoint helloEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, iHelloService);
        endpoint.publish("/IHelloService");
        return endpoint;
    }
}
