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
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DirectoryMonitorInboundFileEndpointTests {

    private BeanFactory beanFactory;

    private File directoryToMonitor;

    @Value("#{fileChannel}")
    private MessageChannel messageChannel;

    @Value("${test-folder}")
    private String folder;

    @Autowired
    private IntegrationTestUtils integrationTestUtils;

    @Before
    public void start() throws Throwable {
        directoryToMonitor = new File(this.folder);

        if (directoryToMonitor.exists()) {
            directoryToMonitor.delete();
        }

        directoryToMonitor.mkdir();
    }

    @Test
    public void testReceivingFiles() throws Throwable {
        final Set<String> files = new ConcurrentSkipListSet<String>();
        integrationTestUtils.createConsumer(this.messageChannel, new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                File file = (File) message.getPayload();
                String filePath = file.getPath();
                files.add(filePath);
            }
        });

        int cnt = 10;
        for (int i = 0; i < cnt; i++) {
            File out = new File(directoryToMonitor, i + ".txt");
            Writer w = new BufferedWriter(new FileWriter(out));
            IOUtils.write("test" + i, w);
            IOUtils.closeQuietly(w);
        }

        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
        Assert.assertEquals(cnt, files.size());
    }
}
