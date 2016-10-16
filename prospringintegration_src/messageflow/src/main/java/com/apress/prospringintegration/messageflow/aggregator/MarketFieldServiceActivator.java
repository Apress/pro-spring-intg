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

package com.apress.prospringintegration.messageflow.aggregator;

import com.apress.prospringintegration.messageflow.domain.Field;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;

@Component
public class MarketFieldServiceActivator {
    @ServiceActivator
    public Field handleField(Field dataField, @Headers Map<String, Object> headerMap) {

        System.out.println(MessageFormat
                .format("{0}:{1}", dataField.getFieldDescriptor().toString(),
                        dataField.getValue()));

        for (String key : headerMap.keySet()) {
            Object value = headerMap.get(key);
            System.out.println(MessageFormat
                    .format("header {0}:{1}", key, value));
        }

        return dataField;
    }
}
