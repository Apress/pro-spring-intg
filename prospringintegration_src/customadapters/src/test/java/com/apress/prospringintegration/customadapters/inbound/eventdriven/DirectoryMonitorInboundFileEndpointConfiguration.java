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

package com.apress.prospringintegration.customadapters.inbound.eventdriven;

import com.apress.prospringintegration.customadapters.inbound.IntegrationTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class DirectoryMonitorInboundFileEndpointConfiguration {

    @Value("#{fileChannel}")
    private MessageChannel messageChannel;

    @Value("${test-folder}")
    private String testFolder;

    private File file;

    @PostConstruct
    public void setup() throws Throwable {
        file = new File(testFolder);
    }

    @Bean
    public IntegrationTestUtils inboundMessageEndpointTestUtils() {
        return new IntegrationTestUtils();
    }

    @Bean
    public DirectoryMonitorInboundFileEndpoint directoryMonitorInboundFileEndpoint() {
        DirectoryMonitorInboundFileEndpoint en = new DirectoryMonitorInboundFileEndpoint();
        en.setOutputChannel(this.messageChannel);
        en.setDirectoryToMonitor(file);

        return en;
    }
}
