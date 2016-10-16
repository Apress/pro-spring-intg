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

package com.apress.prospringintegration.customadapters.inbound.pollerdriven;

import com.apress.prospringintegration.customadapters.inbound.IntegrationTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockPollingMessageSourceConfiguration {

    @Bean
    public IntegrationTestUtils inboundMessageEndpointTestUtils() {
        return new IntegrationTestUtils();
    }

    @Bean
    public StockPollingMessageSource stockPollingMessageSource() {
        StockPollingMessageSource ms = new StockPollingMessageSource();
        ms.setTickerSymbol("VMW");
        return ms;
    }
}
