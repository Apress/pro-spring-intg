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

package com.apress.prospringintegration.customadapters.inbound.pollerdriven.config;

import com.apress.prospringintegration.customadapters.inbound.pollerdriven.StockPollingMessageSource;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class StockNamespaceHandler extends AbstractIntegrationNamespaceHandler {

    public void init() {
        registerBeanDefinitionParser("inbound-channel-adapter",
                new StockPollingMessageSourceParser());
    }

    public class StockPollingMessageSourceParser
            extends AbstractPollingInboundChannelAdapterParser {
        @Override
        protected BeanMetadataElement parseSource(Element element,
                                                  ParserContext parserContext) {
            BeanDefinitionBuilder sourceBuilder =
                    BeanDefinitionBuilder.genericBeanDefinition(
                            StockPollingMessageSource.class.getName());
            IntegrationNamespaceUtils
                    .setValueIfAttributeDefined(sourceBuilder, element,
                            "stock", "tickerSymbol");
            return sourceBuilder.getBeanDefinition();
        }
    }
}

