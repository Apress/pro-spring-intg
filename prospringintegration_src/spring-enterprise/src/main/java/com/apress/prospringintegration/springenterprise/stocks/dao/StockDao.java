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

package com.apress.prospringintegration.springenterprise.stocks.dao;

import com.apress.prospringintegration.springenterprise.stocks.model.Stock;

import java.util.List;

public interface StockDao {
    public void insert(Stock stock);

    public void update(Stock stock);

    public void delete(Stock product);

    public Stock findByInventoryCode(String iCode);

    public List<Stock> findAvailableStockBySymbol(String symbol);

    public List<Stock> get();
}
