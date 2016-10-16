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

package com.apress.prospringintegration.messagestore.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Component
public class MessageProducer {
    private MessagingTemplate messagingTemplate = new MessagingTemplate();

    @Value("#{input}")
    private MessageChannel messageChannel;

    @Value("${correlation-header}")
    private String correlationHeader;

    @PostConstruct
    public void start() throws Throwable {
        this.messagingTemplate.setDefaultChannel(this.messageChannel);
    }

    public void sendMessages(int correlationValue, Collection<String> payloadValues)
            throws Throwable {

        int sequenceNumber = 0;
        int size = payloadValues.size();

        for (String payloadValue : payloadValues) {
            Message<?> message = MessageBuilder.withPayload(payloadValue)
                    .setCorrelationId(this.correlationHeader)
                    .setHeader(this.correlationHeader, correlationValue)
                    .setSequenceNumber(++sequenceNumber)
                    .setSequenceSize(size)
                    .build();
            this.messagingTemplate.send(message);
        }
    }
}
