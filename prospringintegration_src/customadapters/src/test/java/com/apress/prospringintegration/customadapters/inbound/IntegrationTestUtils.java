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

package com.apress.prospringintegration.customadapters.inbound;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.config.ConsumerEndpointFactoryBean;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.endpoint.AbstractEndpoint;

public class IntegrationTestUtils implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory)
            throws BeansException {
        this.beanFactory = beanFactory;
    }

    public AbstractEndpoint createConsumer(MessageChannel messageChannel, MessageHandler messageHandler)
            throws Throwable {
        ConsumerEndpointFactoryBean consumerEndpointFactoryBean = new ConsumerEndpointFactoryBean();
        consumerEndpointFactoryBean.setInputChannel(messageChannel);
        consumerEndpointFactoryBean.setBeanName("MessageConsumer");
        consumerEndpointFactoryBean.setBeanFactory(beanFactory);
        consumerEndpointFactoryBean.setHandler(messageHandler);
        consumerEndpointFactoryBean.setBeanClassLoader(ClassLoader.getSystemClassLoader());
        consumerEndpointFactoryBean.afterPropertiesSet();

        AbstractEndpoint abstractEndpoint = consumerEndpointFactoryBean.getObject();
        abstractEndpoint.start();

        return abstractEndpoint;
    }
}
