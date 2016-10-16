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

package com.apress.prospringintegration.monitoring;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfiguration {
    final private static String JAMON_ID = "jamon";

    @Bean(name = JAMON_ID)
    public JamonPerformanceMonitorInterceptor jamonPerformanceMonitorInterceptor() {
        JamonPerformanceMonitorInterceptor interceptor =
                new JamonPerformanceMonitorInterceptor();
        interceptor.setTrackAllInvocations(true);
        interceptor.setUseDynamicLogger(true);
        return interceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator autoProxyCreator() {
        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
        proxyCreator.setBeanNames(new String[]{"processMessage"});
        proxyCreator.setInterceptorNames(new String[]{"jamon"});
        return proxyCreator;
    }
}
