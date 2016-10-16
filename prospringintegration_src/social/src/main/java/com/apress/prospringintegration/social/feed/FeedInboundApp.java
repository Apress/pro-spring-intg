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

package com.apress.prospringintegration.social.feed;

import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.core.PollableChannel;

public class FeedInboundApp {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("/spring/feed/feed-inbound.xml");

        PollableChannel feedChannel = context.getBean("feedChannel", PollableChannel.class);

        for (int i = 0; i < 10; i++) {
            Message<SyndEntry> message = (Message<SyndEntry>) feedChannel.receive(1000);
            if (message != null) {
                SyndEntry entry = message.getPayload();
                System.out.println(entry.getPublishedDate() + " - " + entry.getTitle());
            } else {
                break;
            }
        }
    }
}
