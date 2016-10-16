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

package com.apress.prospringintegration.springenterprise.stocks.runner;

import com.apress.prospringintegration.springenterprise.stocks.dao.StockDao;
import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Calendar;
import java.util.List;

public class MainHibernateJPA {
    public static void main(String[] args) {
        GenericApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "com.apress.prospringintegration.springenterprise.stocks.dao.jpa");

        StockDao stockDao = context.getBean("jpaStockDao", StockDao.class);
        Stock stock =
                new Stock("ORAC", "JPAMAIN0001", "QQQQ", 120.0f, 1100,
                        Calendar.getInstance().getTime());
        stockDao.insert(stock);

        List<Stock> stocks = stockDao.findAvailableStockBySymbol("ORAC");

        if (stocks != null && stocks.size() > 0) {
            stock = stocks.get(0);
            System.out.println("Stock Symbol :" + stock.getSymbol());
            System.out.println("Inventory Code :" + stock.getInventoryCode());
            System.out.println("purchased price:" + stock.getSharePrice());
            System.out.println("Exchange ID:" + stock.getExchangeId());
            System.out.println("Quantity Available :" + stock.getQuantityAvailable());
        }
    }
}
