//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2020.05.01 时间 01:02:17 PM CST 
//


package com.webservice.ws.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HelloReq" type="{http://service.ws.webservice.com}HelloReq"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "helloReq"
})
@XmlRootElement(name = "HelloRequest")
public class HelloRequest {

    @XmlElement(name = "HelloReq", required = true)
    protected HelloReq helloReq;

    /**
     * 获取helloReq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HelloReq }
     *     
     */
    public HelloReq getHelloReq() {
        return helloReq;
    }

    /**
     * 设置helloReq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HelloReq }
     *     
     */
    public void setHelloReq(HelloReq value) {
        this.helloReq = value;
    }

}
