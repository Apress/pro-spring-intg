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

import com.apress.prospringintegration.webservice.domain.Ticket;
import com.apress.prospringintegration.webservice.domain.TicketRequest;
import com.apress.prospringintegration.webservice.domain.TicketResponse;
import com.apress.prospringintegration.webservice.domain.types.PriorityType;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class TicketWebServiceMarshallingClient {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("client.xml");

        MessageChannel channel =
                context.getBean("ticketRequests", MessageChannel.class);

        MessagingTemplate messagingTemplate = new MessagingTemplate();

        TicketRequest tr = new TicketRequest();
        tr.setDescription("Message Broker Down");
        tr.setPriority(PriorityType.EMERGENCY);
        System.out.printf("Ticket Request: %s [priority: %s] %n", tr.getDescription(),
                tr.getPriority());
        Message<TicketRequest> ticketRequestMessage =
                MessageBuilder.withPayload(tr).build();

        @SuppressWarnings("unchecked")
        Message<TicketResponse> message =
                (Message<TicketResponse>) messagingTemplate.sendAndReceive(
                        channel, ticketRequestMessage);

        Ticket ticket = message.getPayload().getTicket();
        System.out.printf("Ticket Response: %s [id: %d] [priority: %s] [date: %s]%n",
                ticket.getDescription(), ticket.getTicketId(),
                ticket.getPriority(), ticket.getIssueDateTime());

    }
}
