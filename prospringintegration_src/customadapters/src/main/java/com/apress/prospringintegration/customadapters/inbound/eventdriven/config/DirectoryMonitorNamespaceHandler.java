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

package com.apress.prospringintegration.customadapters.inbound.eventdriven.config;

import com.apress.prospringintegration.customadapters.inbound.eventdriven.DirectoryMonitorInboundFileEndpoint;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractIntegrationNamespaceHandler;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

public class DirectoryMonitorNamespaceHandler
        extends AbstractIntegrationNamespaceHandler {

    public void init() {
        registerBeanDefinitionParser("inbound-channel-adapter",
                new DirectoryMonitorInboundFileEndpointParser());
    }

    public class DirectoryMonitorInboundFileEndpointParser
            extends AbstractSingleBeanDefinitionParser {

        @Override
        protected String getBeanClassName(Element element) {
            return DirectoryMonitorInboundFileEndpoint.class.getName();
        }

        @Override
        protected boolean shouldGenerateIdAsFallback() {
            return true;
        }

        @Override
        protected boolean shouldGenerateId() {
            return false;
        }

        @Override
        protected void doParse(Element element, ParserContext parserContext,
                               BeanDefinitionBuilder builder) {
            IntegrationNamespaceUtils.setValueIfAttributeDefined(
                    builder, element, "directory", "directoryToMonitor");
            IntegrationNamespaceUtils.setReferenceIfAttributeDefined(
                    builder, element, "channel", "outputChannel");
        }
    }
}

