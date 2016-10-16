/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apress.prospringintegration.webservice.web;

import com.apress.prospringintegration.webservice.domain.TicketRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MessageEndpoint;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.server.endpoint.mapping.PayloadRootQNameEndpointMapping;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("unused")
@Configuration
public class TicketIssuerServiceConfiguration {
    public static String NAMESPACE = "http://prospringintegration.com/tk/schemas";

    //the ws:inbound-gateway is in fact a reference to this base Spring WS object
    @Value("#{wsInboundGateway}")
    private MessageEndpoint wsInboundGateway;

    @Bean
    public XsdSchema schema() {
        SimpleXsdSchema xsdSchema = new SimpleXsdSchema();
        xsdSchema.setXsd(new ClassPathResource("Ticket.xsd"));
        return xsdSchema;
    }

    @Bean
    public DefaultWsdl11Definition tickets() throws Throwable {
        DefaultWsdl11Definition defaultWsdl11Definition =
                new DefaultWsdl11Definition();
        defaultWsdl11Definition.setSchema(schema());
        defaultWsdl11Definition.setPortTypeName("TicketRequest");
        defaultWsdl11Definition.setLocationUri("/tickets");
        defaultWsdl11Definition.setTargetNamespace(NAMESPACE);

        return defaultWsdl11Definition;
    }

    @Bean
    public PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping() {
        String fqn = String.format("{%s}%s", NAMESPACE, "TicketRequest");
        Map<String, MessageEndpoint> endpoints = new HashMap<String, MessageEndpoint>();
        endpoints.put(fqn, wsInboundGateway);
        PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping =
                new PayloadRootQNameEndpointMapping();
        payloadRootQNameEndpointMapping.setEndpointMap(endpoints);
        payloadRootQNameEndpointMapping.setInterceptors(
                new EndpointInterceptor[]{new PayloadLoggingInterceptor()});
        return payloadRootQNameEndpointMapping;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleHandlerMapping = new SimpleUrlHandlerMapping();
        simpleHandlerMapping.setDefaultHandler(soapMessageDispatcher());
        Properties urlMap = new Properties();
        urlMap.setProperty("*.wsdl", "tickets");
        simpleHandlerMapping.setMappings(urlMap);
        return simpleHandlerMapping;
    }

    @Bean
    public SoapMessageDispatcher soapMessageDispatcher() {
        return new SoapMessageDispatcher();
    }
}