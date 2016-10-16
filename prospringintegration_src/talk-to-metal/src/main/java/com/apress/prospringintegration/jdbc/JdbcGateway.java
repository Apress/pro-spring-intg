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

package com.apress.prospringintegration.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

public class JdbcGateway {
    public static void main(String[] args) throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("/spring/jdbc/jdbc-gateway-context.xml");

        MessageChannel input = context.getBean("input", MessageChannel.class);
        PollableChannel output = context.getBean("output", PollableChannel.class);

        Map<String, Object> rowMessage = new HashMap<String, Object>();

        rowMessage.put("id", 3);
        rowMessage.put("firstname", "Mr");
        rowMessage.put("lastname", "Bill");
        rowMessage.put("status", 0);

        Message<Map<String, Object>> message = MessageBuilder.withPayload(rowMessage).build();
        input.send(message);

        Message<?> reply = output.receive();

        System.out.println("Reply message: " + reply);

        Map<String, Object> rowMap = (Map<String, Object>) reply.getPayload();

        for (String column : rowMap.keySet()) {
            System.out.println("column: " + column + " value: " + rowMap.get(column));
        }
    }
}
