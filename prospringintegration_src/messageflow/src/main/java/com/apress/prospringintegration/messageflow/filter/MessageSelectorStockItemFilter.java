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

package com.apress.prospringintegration.messageflow.filter;

import com.apress.prospringintegration.messageflow.domain.MarketItem;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;
import org.springframework.stereotype.Component;

@Component
public class MessageSelectorStockItemFilter implements MessageSelector {

    @Override
    public boolean accept(Message<?> message) {
        MarketItem item = (MarketItem) message.getPayload();
        return (item != null && item.getType().equals("stock"));
    }
}
