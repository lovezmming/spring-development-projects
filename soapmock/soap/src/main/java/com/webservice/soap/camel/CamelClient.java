package com.webservice.soap.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.soap.SOAPBinding;

import java.util.ArrayList;
import java.util.List;

import static org.apache.cxf.transport.servlet.CXFNonSpringServlet.TRANSPORT_ID;

@Component
@Slf4j
public class CamelClient {
    private final CamelContext camelContext;

    private final ProducerTemplate template;

    @Autowired
    private CamelTLSFactory camelTLSFactory;

    CamelClient() {
        this.camelContext = new DefaultCamelContext();
        this.template = this.camelContext.createProducerTemplate();
    }

    @PostConstruct
    public void init() throws Exception {
        camelContext.disableJMX();
        camelContext.start();
    }

    private void configureTLS(CxfEndpoint cxfEndpoint) {
        Bus bus = new SpringBus();
        ConduitInitiatorManager cim = bus.getExtension(ConduitInitiatorManager.class);
        if (cim != null) {
            cim.registerConduitInitiator(TRANSPORT_ID, camelTLSFactory);
            cxfEndpoint.setTransportId(TRANSPORT_ID);
            cxfEndpoint.setBus(bus);
        }
    }

    public void addRoute(String endpoint, String url) throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {

                CxfEndpoint cxfEndpoint = (CxfEndpoint) getContext().getEndpoint("cxf:" + url);
                configureTLS(cxfEndpoint);
//                cxfEndpoint.setServiceClass(RseSoapApiPortType.class); // HelloApiPort，正常ws经常xjc插件生成的对象
                cxfEndpoint.setDefaultOperationName("方法名，即action：urn对应的名称");
                cxfEndpoint.setBindingId(SOAPBinding.SOAP12HTTP_BINDING);
                cxfEndpoint.setSynchronous(true);

                from("direct:" + endpoint).routeId(endpoint).process(e -> {
                    if (e == null) {
                        return;
                    }
                    Object req = e.getIn().getBody();
                    e.getOut()
                            .setHeader(CxfConstants.OPERATION_NAME, e.getProperties().get(CxfConstants.OPERATION_NAME));
                    setSessionHeader(e);
                    e.getOut().setBody(req);
                }).to(cxfEndpoint).process(e -> {
                    if (e == null) {
                        return;
                    }
                    Class retType = (Class) e.getProperties().get("ReturnType");
                    e.getOut().setBody(e.getIn().getBody(retType));
                });
            }
        });
    }

    public void removeRoute(String uuID) {
        try {
            camelContext.stopRoute(uuID);
            camelContext.removeRoute(uuID);
        } catch (Exception e) {
//            log.error("Failed to remove Route {}", uuID, e);
        }
    }

    private void setSessionHeader(Exchange e) throws JAXBException {
        // Qname填写自定义的命名空间和header
        SoapHeader hdr = new SoapHeader(new QName("Namespace", "SessionHead"),
                new JAXBDataBinding("Header".getClass()));

        List<SoapHeader> soapHeaders = new ArrayList<>();
        soapHeaders.add(hdr);
        e.getOut().setHeader(Header.HEADER_LIST, soapHeaders);
    }

    public Object send(String uuID, String rseOperation, Object req, Class clazz, String sessionID) throws Exception {
        Exchange exchange = new DefaultExchange(camelContext);
        exchange.getIn().setBody(req);
        if (sessionID != null) {
            exchange.getProperties().put("sessionID", sessionID);
        }
        exchange.getProperties().put("ReturnType", clazz);
        exchange.getProperties().put(CxfConstants.OPERATION_NAME, rseOperation);
        template.send("direct:"+ uuID, exchange);
        Exception throwable = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if (throwable != null) {
            throw throwable;
        }
        return exchange.getOut().getBody();
    }

    @PreDestroy
    public void destroy() throws Exception {
//        template.cleanUp();
        camelContext.stop();
    }
}