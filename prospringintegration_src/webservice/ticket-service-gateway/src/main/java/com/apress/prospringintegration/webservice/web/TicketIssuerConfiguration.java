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

import com.apress.prospringintegration.webservice.CommonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.server.endpoint.MessageEndpoint;
import org.springframework.ws.server.endpoint.mapping.UriEndpointMapping;

@Configuration
public class TicketIssuerConfiguration extends CommonConfiguration {

    //the ws:inbound-gateway is in fact a reference to this base Spring WS object
    @Value("#{wsInboundGateway}")
    private MessageEndpoint wsInboundGateway;

    @Bean
    public UriEndpointMapping uriEndpointMapping() {
        UriEndpointMapping uriEndpointMapping = new UriEndpointMapping();
        uriEndpointMapping.setDefaultEndpoint(wsInboundGateway);
        return uriEndpointMapping;
    }

}
