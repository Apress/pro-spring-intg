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

package com.apress.prospringintegration.messaging.hornetq.jms.adapter;

import com.apress.prospringintegration.messaging.hornetq.jms.adapter.Ticket.Priority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class TicketGenerator {

    private long nextTicketId;

    public TicketGenerator() {
        this.nextTicketId = 1000l;
    }

    public List<Ticket> createTickets() {
        List<Ticket> tickets = new ArrayList<Ticket>();

        tickets.add(createLowPriorityTicket());
        tickets.add(createLowPriorityTicket());
        tickets.add(createLowPriorityTicket());
        tickets.add(createLowPriorityTicket());
        tickets.add(createLowPriorityTicket());
        tickets.add(createMediumPriorityTicket());
        tickets.add(createMediumPriorityTicket());
        tickets.add(createMediumPriorityTicket());
        tickets.add(createMediumPriorityTicket());
        tickets.add(createMediumPriorityTicket());
        tickets.add(createHighPriorityTicket());
        tickets.add(createHighPriorityTicket());
        tickets.add(createHighPriorityTicket());
        tickets.add(createHighPriorityTicket());
        tickets.add(createHighPriorityTicket());
        tickets.add(createEmergencyTicket());
        tickets.add(createEmergencyTicket());
        tickets.add(createEmergencyTicket());
        tickets.add(createEmergencyTicket());
        tickets.add(createEmergencyTicket());

        return tickets;
    }

    Ticket createEmergencyTicket() {
        return createTicket(Priority.emergency, "Urgent problem. Fix it ASAP or lose revenue!!!");
    }

    Ticket createHighPriorityTicket() {
        return createTicket(Priority.high, "Some serious issue. Fix it now!");
    }

    Ticket createMediumPriorityTicket() {
        return createTicket(Priority.medium, "There is an issue. Take a look whenever you have time.");
    }

    Ticket createLowPriorityTicket() {
        return createTicket(Priority.low, "Some not so important problems have been found.");
    }

    Ticket createTicket(Priority priority, String description) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(nextTicketId++);
        ticket.setPriority(priority);
        ticket.setIssueDateTime(GregorianCalendar.getInstance());
        ticket.setDescription(description);

        return ticket;
    }
}
