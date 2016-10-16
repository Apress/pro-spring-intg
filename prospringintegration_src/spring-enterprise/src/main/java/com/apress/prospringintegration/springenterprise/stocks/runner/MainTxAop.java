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

import com.apress.prospringintegration.springenterprise.stocks.dao.StockBrokerService;
import com.apress.prospringintegration.springenterprise.stocks.dao.StockDao;
import com.apress.prospringintegration.springenterprise.stocks.dao.hibernate.HibernateStockDao;
import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class MainTxAop {
    public static void main(String[] args) throws Exception {
        GenericApplicationContext context = new AnnotationConfigApplicationContext(
            "com.apress.prospringintegration.springenterprise.stocks.transactions.aopdeclarative",
            "com.apress.prospringintegration.springenterprise.stocks.dao.hibernate");

        StockBrokerService broker =
                context.getBean("aopStockBrokerService", StockBrokerService.class);
        try {
            broker.preFillStocks("QQQQ", "INTC", "IBM", "XLA", "MGM", "C");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        StockDao stockDao = context.getBean(HibernateStockDao.class);
        System.out.println("Total rows inserted: " + stockDao.get().size());
        for (Stock s : stockDao.get()) {
            System.out.println("Stock added: " + s.getSymbol());
        }
    }
}
