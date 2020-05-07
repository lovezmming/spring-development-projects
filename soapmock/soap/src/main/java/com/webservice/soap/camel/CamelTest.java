package com.webservice.soap.camel;

import com.webservice.soap.ws.service.HelloRequest;
import com.webservice.soap.ws.service.HelloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamelTest {

    @Autowired
    private CamelClient camelClient;

    @PostMapping("/camel")
    public void testCamel() throws Exception {
        camelClient.addRoute("test", "http://localhost:8080/services/Hello?wsdl");
        camelClient.send("test", "hello", new HelloRequest(), HelloResponse.class, "1234");
        camelClient.destroy();
    }
}
