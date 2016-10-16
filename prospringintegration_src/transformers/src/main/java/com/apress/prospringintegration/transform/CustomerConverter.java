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

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerConverter implements Converter<Map<String, String>, Customer> {
    @Override
    public Customer convert(Map<String, String> customerMap) {
        Customer customer = new Customer();

        customer.setFirstName(customerMap.get("firstName"));
        customer.setLastName(customerMap.get("lastName"));
        customer.setAddress(customerMap.get("address"));
        customer.setCity(customerMap.get("city"));
        customer.setState(customerMap.get("state"));
        customer.setZip(customerMap.get("zip"));
        return customer;
    }
}
