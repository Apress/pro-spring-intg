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
import com.apress.prospringintegration.springenterprise.stocks.dao.jdbc.JdbcTemplateStockDao;
import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import com.apress.prospringintegration.springenterprise.stocks.transactions.template.TransactionalStockBrokerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class MainTxJdbc {
    public static void main(String[] args) {
        GenericApplicationContext context = new AnnotationConfigApplicationContext(
            "com.apress.prospringintegration.springenterprise.stocks.transactions.template",
            "com.apress.prospringintegration.springenterprise.stocks.dao.jdbc");

        TransactionalStockBrokerService uStockDao =
                context.getBean("transactionalStockBrokerService",
                        TransactionalStockBrokerService.class);
        try {
            uStockDao.preFillStocks("TEST", "IBM", "INTC", "MSFT", "ORAC", "C");
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        StockDao stockDao = context.getBean(JdbcTemplateStockDao.class);
        System.out.println("Total rows inserted: " + stockDao.get().size());
        for (Stock s : stockDao.get()) {
            System.out.println("Stock added: " + s.getSymbol());
        }
    }
}
