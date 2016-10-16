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

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Mapper {

    @Transformer
    public Customer map(Map<String, String> message) {
        Customer customer = new Customer();

        customer.setFirstName(message.get("firstName"));
        customer.setLastName(message.get("lastName"));
        customer.setAddress(message.get("address"));
        customer.setCity(message.get("city"));
        customer.setState(message.get("state"));
        customer.setZip(message.get("zip"));
        return customer;
    }
}
