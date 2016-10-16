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

import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HeaderMapper {

    @Transformer
    public Message<Customer> map(Message<Map<String, String>> message) {

        Map<String, String> mapMessage = message.getPayload();
        Customer customer = new Customer();

        customer.setFirstName(mapMessage.get("firstName"));
        customer.setLastName(mapMessage.get("lastName"));
        customer.setAddress(mapMessage.get("address"));
        customer.setCity(mapMessage.get("city"));
        customer.setState(mapMessage.get("state"));
        customer.setZip(mapMessage.get("zip"));

        return MessageBuilder.withPayload(customer)
                .copyHeadersIfAbsent(message.getHeaders())
                .setHeaderIfAbsent("count", 12).build();
    }
}
