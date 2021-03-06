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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class StockPollingMessageSourceTests {

    @Autowired
    private IntegrationTestUtils integrationTestUtils;

    @Value("#{stockChannel}")
    private MessageChannel messageChannel;

    @Test
    public void testReceivingStockInformation() throws Throwable {
        this.integrationTestUtils.createConsumer(messageChannel, new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                Stock stock = (Stock) message.getPayload();
                System.out.println("stock:" + stock);
            }
        });
        Thread.sleep(TimeUnit.SECONDS.toMillis(30));
    }
}
