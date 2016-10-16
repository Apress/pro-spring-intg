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

package com.apress.prospringintegration.springenterprise.stocks.transactions.aopdeclarative;

import com.apress.prospringintegration.springenterprise.stocks.dao.StockBrokerService;
import com.apress.prospringintegration.springenterprise.stocks.dao.StockDao;
import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

@Component("aopStockBrokerService")
public class AOPStockBrokerService implements StockBrokerService {

    @Autowired
    @Qualifier("hibernateStockDao")
    private StockDao stockDao;

    private Random random = new Random();

    // inserts at least 25% duplicate inventory code.
    public void preFillStocks(final String exchangeId, final String... symbols) {
        int i = 0;
        for (String sym : symbols) {
            float pp = (float) Math.random() * 100.0f;
            int qq = (int) Math.random() * 250;
            Stock s = new Stock(sym, "INV00" + i, exchangeId, pp, qq, Calendar
                    .getInstance().getTime());
            stockDao.insert(s);
            System.out.println("ORIG INVENTORY: " + s.getInventoryCode() + " ");
            int randomized = (random.nextInt(100) % 4) == 0 ? 0 : i;
            s.setInventoryCode("INV00" + randomized);
            System.out.println("NEW RANDOMIZED INVENTORY:"
                    + s.getInventoryCode() + " " + randomized);
            stockDao.update(s);
            i++;
        }
    }
}

