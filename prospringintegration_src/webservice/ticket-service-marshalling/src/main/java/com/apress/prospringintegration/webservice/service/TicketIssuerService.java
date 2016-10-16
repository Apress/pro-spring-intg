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

package com.apress.prospringintegration.webservice.service;

import com.apress.prospringintegration.webservice.domain.Ticket;
import com.apress.prospringintegration.webservice.domain.types.PriorityType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

@Component
public class TicketIssuerService {
    public Ticket issueTicket(String description, String priority) throws Exception {
        Ticket ticket = new Ticket();
        ticket.setDescription(description);
        ticket.setPriority(PriorityType.valueOf(priority));
        ticket.setTicketId(new Random().nextLong() * 1000);
        ticket.setIssueDateTime(new Date());

        return ticket;
    }
}
