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

package com.apress.prospringintegration.endpoints.pollingconsumer;

import com.apress.prospringintegration.endpoints.core.Ticket;
import com.apress.prospringintegration.endpoints.core.TicketGenerator;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.List;

public class Main {
    private final static int RECEIVE_TIMEOUT = 1000;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("polling-consumer.xml");
        applicationContext.start();

        ProblemReporter problemReporter =
                applicationContext.getBean(ProblemReporter.class);
        TicketMessageHandler ticketMessageHandler =
                applicationContext.getBean(TicketMessageHandler.class);
        TicketGenerator ticketGenerator =
                applicationContext.getBean(TicketGenerator.class);

        QueueChannel channel =
                applicationContext.getBean("ticketChannel", QueueChannel.class);

        // Define the polling consumer
        PollingConsumer ticketConsumer =
                new PollingConsumer(channel, ticketMessageHandler);
        ticketConsumer.setReceiveTimeout(RECEIVE_TIMEOUT);
        ticketConsumer.setBeanFactory(applicationContext);

        // Setup the poller using periodic trigger
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(1000);
        periodicTrigger.setInitialDelay(5000);
        periodicTrigger.setFixedRate(false);

        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(periodicTrigger);
        pollerMetadata.setMaxMessagesPerPoll(3);

        ticketConsumer.setPollerMetadata(pollerMetadata);

        // Starts the polling consumer in the other thread
        ticketConsumer.start();

        // Generates messages and sends to the channel
        List<Ticket> tickets = ticketGenerator.createTickets();
        while (true) {
            for (Ticket ticket : tickets) {
                problemReporter.openTicket(ticket);
            }
        }
    }
}
