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
import com.apress.prospringintegration.messageflow.domain.FieldDescriptor;
import com.apress.prospringintegration.messageflow.domain.MarketItem;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarketDataAggregator {

    @Aggregator
    public MarketItem handleFieldData(List<Field> fields) {
        MarketItem marketItem = new MarketItem();

        for (Field field : fields) {
            if (field.getFieldDescriptor().equals(FieldDescriptor.TYPE)) {
                marketItem.setType(field.getValue());
            } else if (field.getFieldDescriptor().equals(FieldDescriptor.SYMBOL)) {
                marketItem.setSymbol(field.getValue());
            } else if (field.getFieldDescriptor().equals(FieldDescriptor.PRICE)) {
                marketItem.setPrice(field.getValue());
            } else if (field.getFieldDescriptor().equals(FieldDescriptor.OPEN_PRICE)) {
                marketItem.setOpenPrice(field.getValue());
            } else if (field.getFieldDescriptor().equals(FieldDescriptor.DESC)) {
                marketItem.setDescription(field.getValue());
            }
        }

        return marketItem;
    }
}
