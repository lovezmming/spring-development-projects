package com.webservice.soap.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.springframework.stereotype.Component;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
class CamelTLSFactory implements ConduitInitiator {
    private final HTTPTransportFactory factory = new HTTPTransportFactory();

    @Override
    public Conduit getConduit(EndpointInfo endpointInfo, Bus bus) throws IOException {
        HTTPConduit httpConduit = (HTTPConduit) factory.getConduit(endpointInfo, bus);
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(3000);
        httpClientPolicy.setReceiveTimeout(3000);
        httpConduit.setClient(httpClientPolicy);
        httpConduit.setTlsClientParameters(createTlsClientParameters());
        return httpConduit;
    }

    @Override
    public Conduit getConduit(EndpointInfo endpointInfo, EndpointReferenceType target, Bus bus) throws IOException {
        HTTPConduit httpConduit = (HTTPConduit) factory.getConduit(endpointInfo, target, bus);
        httpConduit.setTlsClientParameters(createTlsClientParameters());
        return httpConduit;
    }

    @Override
    public Set<String> getUriPrefixes() {
        return factory.getUriPrefixes();
    }

    @Override
    public List<String> getTransportIds() {
        return factory.getTransportIds();
    }

    private TLSClientParameters createTlsClientParameters() throws IOException {
        TLSClientParameters tlsClientParameters = new TLSClientParameters();

        // DisableCNCheck
        tlsClientParameters.setDisableCNCheck(true);

        try {
            tlsClientParameters.setTrustManagers(new TrustManager[]{
            });
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        return tlsClientParameters;
    }
}