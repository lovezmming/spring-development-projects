package com.webservice.ws.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebserviceConfig extends WsConfigurerAdapter {
    private static final String NAMESPACE_URI = "http://service.ws.webservice.com";

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/hello/*");
    }

    @Bean(name = "HelloApi")
    public Wsdl11Definition defaultWsdl11Definition(XsdSchema rseMockSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("HelloApiPort");
        wsdl11Definition.setLocationUri("/hello");
        wsdl11Definition.setTargetNamespace(NAMESPACE_URI);
        wsdl11Definition.setSchema(rseMockSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema rseMockSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/hello.xsd"));
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return messageFactory;
    }

}