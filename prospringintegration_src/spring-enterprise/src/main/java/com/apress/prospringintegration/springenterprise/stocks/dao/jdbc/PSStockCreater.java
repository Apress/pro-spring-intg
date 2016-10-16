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

package com.apress.prospringintegration.springenterprise.stocks.dao.jdbc;

import com.apress.prospringintegration.springenterprise.stocks.model.Stock;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PSStockCreater implements PreparedStatementCreator {

    private Stock stock;

    public PSStockCreater(Stock stock) {
        this.stock = stock;
    }

    public PreparedStatement createPreparedStatement(Connection connection)
            throws SQLException {
        String sql =
                "INSERT INTO " +
                        "STOCKS (SYMBOL, INVENTORY_CODE, PRICE_PER_SHARE," +
                        "QUANTITY_AVAILABLE, EXCHANGE_ID, PURCHASE_DATE) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps =
                connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, stock.getSymbol());
        ps.setString(2, stock.getInventoryCode());
        ps.setFloat(3, stock.getSharePrice());
        ps.setFloat(4, stock.getQuantityAvailable());
        ps.setString(5, stock.getExchangeId());
        ps.setDate(6, new java.sql.Date(stock.getPurchaseDate().getTime()));
        return ps;
    }
}
