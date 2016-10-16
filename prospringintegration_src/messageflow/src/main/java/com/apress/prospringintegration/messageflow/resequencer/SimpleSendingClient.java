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

package com.apress.prospringintegration.messageflow.resequencer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SimpleSendingClient {

    @Autowired
    @Qualifier("inboundChannel")
    private MessageChannel channel;

    public void kickOff() {
        List<Bid> bids = getBids();
        for (Bid b : bids) {
            b.setTime(new Date());
            Message<Bid> message = MessageBuilder.withPayload(b)
                    .setCorrelationId("BID").setSequenceNumber(b.getOrder())
                    .setSequenceSize(bids.size()).build();
            channel.send(message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }

    /**
     * Generate a list of bids with some time in-between bid
     */
    public List<Bid> getBids() {
        List<Bid> bids = new ArrayList<Bid>();
        for(int order = 5; order > 0; order--) {
            Bid bid = new Bid();
            bid.setOrder(order);
            bids.add(bid);
        }
        return bids;
    }
}
