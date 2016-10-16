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

package com.apress.prospringintegration.gateways.service;

import com.apress.prospringintegration.gateways.client.TicketIssuer;
import com.apress.prospringintegration.gateways.model.Ticket;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TicketIssuerService {
    @ServiceActivator
    public Ticket issueTicket(long ticketId) {
        Ticket t = new Ticket();
        t.setIssueDateTime(new Date());
        t.setDescription("New Ticket");
        t.setPriority(Ticket.Priority.medium);
        t.setTicketId(ticketId);

        System.out.println("Issuing a Ticket: " + t.getIssueDateTime());

        return t;
    }
}
