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

package com.apress.prospringintegration.webservice.web;

import com.apress.prospringintegration.webservice.domain.Ticket;
import com.apress.prospringintegration.webservice.domain.TicketRequest;
import com.apress.prospringintegration.webservice.domain.TicketResponse;
import com.apress.prospringintegration.webservice.service.TicketIssuerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class TicketIssuerMarshallingEndpoint {

    @Autowired
    private TicketIssuerService ticketIssuerService;

    @ServiceActivator
    public TicketResponse handleRequest(TicketRequest tr) throws Exception {
        System.out.println("TicketRequest: " + tr);
        TicketResponse ticketResponse = new TicketResponse();
        Ticket t = ticketIssuerService.issueTicket(tr.getDescription(),
                tr.getPriority().name());
        ticketResponse.setTicket(t);
        return ticketResponse;
    }
}
