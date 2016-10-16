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

package com.apress.prospringintegration.messaging.qpid.jms.adapter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TicketReporterMain {

    public static void main(String[] args) throws Throwable {
        String contextName = "ticket-reporter.xml";

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(contextName);
        applicationContext.start();

        ProblemReporter problemReporter = applicationContext.getBean(ProblemReporter.class);
        TicketGenerator ticketGenerator = applicationContext.getBean(TicketGenerator.class);

        while (true) {
            List<Ticket> tickets = ticketGenerator.createTickets();
            for (Ticket ticket : tickets) {
                problemReporter.openTicket(ticket);
            }

            Thread.sleep(5000);
        }
    }
}
