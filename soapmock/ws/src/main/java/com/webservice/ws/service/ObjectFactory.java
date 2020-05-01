//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2020.05.01 时间 01:02:17 PM CST 
//


package com.webservice.ws.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hello.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloReqUsername_QNAME = new QName("http://service.ws.webservice.com", "username");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hello.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link HelloResp }
     * 
     */
    public HelloResp createHelloResp() {
        return new HelloResp();
    }

    /**
     * Create an instance of {@link HelloRequest }
     * 
     */
    public HelloRequest createHelloRequest() {
        return new HelloRequest();
    }

    /**
     * Create an instance of {@link HelloReq }
     * 
     */
    public HelloReq createHelloReq() {
        return new HelloReq();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.webservice.com", name = "username", scope = HelloReq.class)
    public JAXBElement<String> createHelloReqUsername(String value) {
        return new JAXBElement<String>(_HelloReqUsername_QNAME, String.class, HelloReq.class, value);
    }

}
