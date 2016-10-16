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

package com.apress.prospringintegration.springenterprise.stocks.dao.jpa;

import com.apress.prospringintegration.springenterprise.stocks.dao.StockDao;
import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@TransactionConfiguration(transactionManager = "transactionManager")
@Component
public class JpaStockDao implements StockDao {
    @Autowired
    private JpaTemplate jpaTemplate;

    @Override
    @Transactional
    public void insert(Stock stock) {
        jpaTemplate.persist(stock);
    }

    @Override
    @Transactional
    public void update(Stock stock) {
        jpaTemplate.merge(stock);
    }

    @Override
    @Transactional
    public void delete(Stock stock) {
        jpaTemplate.remove(stock);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Stock> findAvailableStockBySymbol(String symbol) {
        return (List<Stock>) jpaTemplate.find("from Stock s where s.symbol=?", symbol);
    }

    @Override
    @Transactional(readOnly = true)
    public Stock findByInventoryCode(String iCode) {
        return jpaTemplate.find(Stock.class, iCode);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Stock> get() {
        return jpaTemplate.find("from Stock s");
    }
}
