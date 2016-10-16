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

package com.apress.prospringintegration.springenterprise.stocks.service;

import com.apress.prospringintegration.springenterprise.stocks.model.Quote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuoteServiceImpl implements QuoteService {

    private Map<String, Quote> allQuotes = new HashMap<String, Quote>();

    @Override
    public Quote getQuote(String symbol) {
        if (allQuotes.containsKey(symbol)) {
            return allQuotes.get(symbol);
        }
        
        Quote q = new Quote(symbol, (float) Math.random() * 100, "NYSE");
        allQuotes.put(symbol, q);
        return q;
    }

    @Override
    public List<String> getAllSymbols() {
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(allQuotes.keySet());
        return keyList;
    }
}
