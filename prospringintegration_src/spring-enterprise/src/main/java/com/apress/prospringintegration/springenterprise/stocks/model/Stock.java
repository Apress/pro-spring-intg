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

package com.apress.prospringintegration.springenterprise.stocks.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "STOCKS")
public class Stock {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "INVENTORY_CODE", nullable = false)
    String inventoryCode;

    @Column(name = "SYMBOL")
    String symbol;

    @Column(name = "EXCHANGE_ID")
    String exchangeId;

    @Column(name = "PRICE_PER_SHARE")
    float sharePrice;

    @Column(name = "QUANTITY_AVAILABLE")
    int quantityAvailable;

    @Column(name = "PURCHASE_DATE")
    Date purchaseDate;

    public Stock() {
    }

    public Stock(String symbol, String inventoryCode, String exchange,
                 float purchasedPrice, int quantityAvailable, Date purchaseDate) {
        this.symbol = symbol;
        this.inventoryCode = inventoryCode;
        this.exchangeId = exchange;
        this.sharePrice = purchasedPrice;
        this.quantityAvailable = quantityAvailable;
        this.purchaseDate =
                purchaseDate != null ? purchaseDate : Calendar.getInstance().getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public float getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(float sharePrice) {
        this.sharePrice = sharePrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", inventoryCode='" + inventoryCode + '\'' +
                ", symbol='" + symbol + '\'' +
                ", exchangeId='" + exchangeId + '\'' +
                ", sharePrice=" + sharePrice +
                ", quantityAvailable=" + quantityAvailable +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
