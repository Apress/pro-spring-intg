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

package com.apress.prospringintegration.endpoints.consumerendpointfactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.core.MessageHandler;

@Configuration
public class ConsumerEndpointConfiguration {

    @Value("#{ticketChannel}")
    private MessageChannel inputChannel;

    @Value("#{ticketMessageHandler}")
    private MessageHandler handler;

    @Bean
    public ConsumerEndpointFactoryBean consumerEndpoint() {
        ConsumerEndpointFactoryBean factoryBean = new ConsumerEndpointFactoryBean();
        factoryBean.setInputChannel(inputChannel);
        factoryBean.setHandler(handler);
        // Need to set pollerMetadata for polling consumer
        //factoryBean.setPollerMetadata(pollerMetadata);
        return factoryBean;
    }
}
