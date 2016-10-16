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

package com.apress.prospringintegration.channels.queuechannel;

import com.apress.prospringintegration.channels.core.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class EmergencyTicketReceiver extends TicketReceiver {

    private MessageSelector emergencyTicketSelector;

    @Autowired
    public void setEmergencyTicketSelector(MessageSelector emergencyTicketSelector) {
        this.emergencyTicketSelector = emergencyTicketSelector;
    }

    @Override
    public void handleTicketMessage() {
        Message<?> ticketMessage = null;

        while (true) {
            List<Message<?>> emergencyTicketMessages = channel.purge(emergencyTicketSelector);
            handleEmergencyTickets(emergencyTicketMessages);

            ticketMessage = channel.receive(RECEIVE_TIMEOUT);
            if (ticketMessage != null) {
                handleTicket((Ticket) ticketMessage.getPayload());
            } else {
                try {
                    /** Handle some other tasks **/
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    void handleEmergencyTickets(List<Message<?>> highPriorityTicketMessages) {
        Assert.notNull(highPriorityTicketMessages);
        for (Message<?> ticketMessage : highPriorityTicketMessages) {
            handleTicket((Ticket) ticketMessage.getPayload());
        }
    }
}
