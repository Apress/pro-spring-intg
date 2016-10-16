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

package com.apress.prospringintegration.springenterprise.stocks.dao.jpaandentitymanagers;


import com.apress.prospringintegration.springenterprise.stocks.dao.StockDao;
import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import org.springframework.stereotype.Component;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@TransactionConfiguration(transactionManager = "transactionManager")
@Component
public class JpaStockDao implements StockDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Stock stock) {
        this.entityManager.persist(stock);
    }

    @Override
    @Transactional
    public void update(Stock stock) {
        this.entityManager.merge(stock);
    }


    @Override
    @Transactional
    public void delete(Stock stock) {
        entityManager.remove(stock);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Stock> findAvailableStockBySymbol(String symbol) {
        Query qu = entityManager.createQuery(
                " from Stock s where s.symbol = :stock ");
        qu.setParameter("stock", symbol);
        return (List<Stock>) qu.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Stock findByInventoryCode(String iCode) {
        return entityManager.find(Stock.class, iCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> get() {
        return (List<Stock>) entityManager.createQuery("from Stock").getResultList();
    }
}
