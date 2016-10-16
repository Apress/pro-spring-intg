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

package com.apress.prospringintegration.messaging.activemq.jms.backedchannel;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.core.SubscribableChannel;

import java.util.List;

public class TicketMain {

    public static void main(String[] args) {

        String contextName = "jms-backedchannel.xml";

        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(contextName);
        applicationContext.start();

        TicketCreator ticketCreator =
                applicationContext.getBean(TicketCreator.class);
        TicketGenerator ticketGenerator =
                applicationContext.getBean(TicketGenerator.class);
        TicketMessageHandler ticketMessageHandler =
                applicationContext.getBean(TicketMessageHandler.class);

        SubscribableChannel channel =
                applicationContext.getBean("ticketChannel", SubscribableChannel.class);
        channel.subscribe(ticketMessageHandler);

        while (true) {
            List<Ticket> tickets = ticketGenerator.createTickets();
            for (Ticket ticket : tickets) {
                ticketCreator.openTicket(ticket);
            }
        }
    }
}
