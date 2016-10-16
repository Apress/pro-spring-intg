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

package com.apress.prospringintegration.messageflow.splitter;

import com.apress.prospringintegration.messageflow.domain.Field;
import com.apress.prospringintegration.messageflow.domain.FieldDescriptor;
import com.apress.prospringintegration.messageflow.domain.MarketItem;
import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MarketDataSplitter {
    /* Splitter that produces individual fields for aggregation */
    @Splitter
    public Collection<Field> splitItem(MarketItem marketItem) {
        List<Field> messages = new ArrayList<Field>();

        Field field = new Field(FieldDescriptor.SYMBOL, marketItem.getSymbol());
        messages.add(field);

        field = new Field(FieldDescriptor.DESC, marketItem.getDescription());
        messages.add(field);

        field = new Field(FieldDescriptor.PRICE, marketItem.getPrice());
        messages.add(field);

        field = new Field(FieldDescriptor.OPEN_PRICE, marketItem.getOpenPrice());
        messages.add(field);

        field = new Field(FieldDescriptor.TYPE, marketItem.getType());
        messages.add(field);

        return messages;
    }
}
