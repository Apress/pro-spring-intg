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

package com.apress.prospringintegration.webservice.client;

import com.apress.prospringintegration.webservice.CommonConfiguration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;

public class TicketWebServiceDomClient {
    private static String bodyTemplate =
            "<TicketRequest xmlns=\"" + CommonConfiguration.NAMESPACE + "\">" +
                    "<description>%s</description>" +
                    "<priority>%s</priority>" +
                    "</TicketRequest>";

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("client.xml");

        MessageChannel channel = context.getBean("ticketRequests", MessageChannel.class);

        String body = String.format(bodyTemplate, "Message Broker Down", "emergency");
        System.out.println(body);
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        Message<?> message = messagingTemplate.sendAndReceive(
                channel, MessageBuilder.withPayload(body).build());

        System.out.println(message.getPayload());

    }

}
