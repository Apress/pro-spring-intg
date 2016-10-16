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

package com.apress.prospringintegration.transform;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.core.PollableChannel;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

public class IntegrationTransformer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:integration-transformer.xml");

        JmsTemplate jmsTemplate = context.getBean("jmsTemplate", JmsTemplate.class);

        jmsTemplate.send(new MessageCreator() {

            @Override
            public javax.jms.Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("firstName", "John");
                message.setString("lastName", "Smith");
                message.setString("address", "100 State Street");
                message.setString("city", "Los Angeles");
                message.setString("state", "CA");
                message.setString("zip", "90064");
                System.out.println("Sending message: " + message);
                return message;
            }
        });

        PollableChannel output = (PollableChannel) context.getBean("output");
        Message<?> reply = output.receive();
        System.out.println("received: " + reply.getPayload());
    }
}
