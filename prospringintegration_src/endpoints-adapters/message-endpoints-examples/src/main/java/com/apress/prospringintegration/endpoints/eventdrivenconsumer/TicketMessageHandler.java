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

package com.apress.prospringintegration.endpoints.eventdrivenconsumer;

import com.apress.prospringintegration.endpoints.core.Ticket;
import org.springframework.integration.Message;
import org.springframework.integration.MessageDeliveryException;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.MessageRejectedException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class TicketMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message)
            throws MessageHandlingException, MessageDeliveryException {
        Object payload = message.getPayload();

        if (payload instanceof Ticket) {
            handleTicket((Ticket) payload);
        } else {
            throw new MessageRejectedException(message,
                    "Unknown data type has been received.");
        }
    }

    void handleTicket(Ticket ticket) {
        System.out.println("Received ticket - " + ticket.toString());
    }
}
