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

package com.apress.prospringintegration.springenterprise.stocks.client;

import com.apress.prospringintegration.springenterprise.stocks.service.QuoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class RMIClientConfig {

    @Bean
    public QuoteService quoteServiceBean() {
        RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
        factory.setServiceUrl("rmi://localhost:1099/QuoteService");
        factory.setServiceInterface(QuoteService.class);
        factory.afterPropertiesSet();
        return (QuoteService) factory.getObject();
    }

    @Bean
    public QuoteServiceClient client() {
        QuoteServiceClient qc = new QuoteServiceClient();
        qc.setMyQuoteService(quoteServiceBean());
        return qc;
    }
}
