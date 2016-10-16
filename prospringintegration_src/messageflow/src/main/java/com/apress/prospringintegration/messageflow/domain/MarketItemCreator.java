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

package com.apress.prospringintegration.messageflow.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MarketItemCreator {

    public List<MarketItem> getMarketItems() {
        List<MarketItem> marketItems = new ArrayList<MarketItem>();

        MarketItem marketItem = new MarketItem();
        marketItem.setSymbol("IBM");
        marketItem.setDescription("International Business Machines");
        marketItem.setOpenPrice("130.00");
        marketItem.setPrice("135.00");
        marketItem.setType("stock");
        marketItems.add(marketItem);

        marketItem = new MarketItem();
        marketItem.setSymbol("PBNDXX");
        marketItem.setDescription("A Par Bond");
        marketItem.setOpenPrice("50.00");
        marketItem.setPrice("55.00");
        marketItem.setType("bond");
        marketItems.add(marketItem);

        marketItem = new MarketItem();
        marketItem.setSymbol("MUFX");
        marketItem.setDescription("Mutual Bonds");
        marketItem.setOpenPrice("50.00");
        marketItem.setPrice("55.00");
        marketItem.setType("bond");
        marketItems.add(marketItem);

        marketItem = new MarketItem();
        marketItem.setSymbol("stock");
        marketItem.setDescription("Intel Corp.");
        marketItem.setOpenPrice("130.00");
        marketItem.setPrice("135.00");
        marketItem.setType("stock");
        marketItems.add(marketItem);

        return marketItems;
    }
}
