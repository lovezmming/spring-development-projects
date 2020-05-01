package com.webservice.soap.ws;

import com.webservice.soap.ws.service.HelloRequest;
import com.webservice.soap.ws.service.HelloResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

public class WebserviceSoapTest {
    public static void main(String[] args) {
        // 支持soap1.1
        String url = "http://localhost:8080/hello/HelloApi.wsdl";
        try {
            WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
            webServiceTemplate.setDefaultUri(url);
            Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
            jaxb2Marshaller.setClassesToBeBound(HelloRequest.class, HelloResponse.class);
            webServiceTemplate.setMarshaller(jaxb2Marshaller);
            webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
            HelloRequest helloRequest = new HelloRequest();
            // helloRequest.setHelloReq();
            HelloResponse response = (HelloResponse) webServiceTemplate.marshalSendAndReceive(helloRequest);
        } catch (Exception e) {
        }
    }
}
