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

package com.apress.prospringintegration.messaging.hornetq.jms.adapter;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.jms.connection.CachingConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HornetqConfiguration {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory =
                new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(10);
        cachingConnectionFactory.setCacheProducers(false);
        cachingConnectionFactory.setTargetConnectionFactory(hornetQConnectionFactory());
        return cachingConnectionFactory;
    }

    @Bean
    public HornetQConnectionFactory hornetQConnectionFactory() {
        HornetQConnectionFactory connectionFactory =
                new HornetQConnectionFactory(transportConfiguration());
        return connectionFactory;
    }

    @Bean
    public TransportConfiguration transportConfiguration() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("host", "localhost");
        map.put("port", 5445);
        TransportConfiguration configuration =
                new TransportConfiguration(
                        "org.hornetq.core.remoting.impl.netty.NettyConnectorFactory", map);
        return configuration;
    }

    @Bean
    public MessageChannel ticketChannel() {
        MessageChannel channel = new DirectChannel();
        return channel;
    }
}
