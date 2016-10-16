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

package com.apress.prospringintegration.customadapters.inbound.pollerdriven;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockPollingMessageSource implements MessageSource<Stock>, InitializingBean {
    private String tickerSymbol;
    private String jsonFragmentTemplate = "u:\"/finance?q=NYSE:%s\",name:\"%s\"";
    private String stockServiceUrl = "http://www.google.com/finance?q=NYSE:%s";
    private RestTemplate restTemplate = new RestTemplate();
    private Pattern symbolSize = Pattern.compile(",p:\"(\\d+)\\.(\\d+)\"");

    protected Stock getStockInformationFor(final String symbol) {
        String url = String.format(this.stockServiceUrl, symbol);
        Stock stock = restTemplate.execute(url, HttpMethod.GET, null,
                new ResponseExtractor<Stock>() {
                    @Override
                    public Stock extractData(ClientHttpResponse clientHttpResponse)
                            throws IOException {
                        String fragPattern =
                                String.format(jsonFragmentTemplate, symbol, symbol);
                        String bodyAsText = IOUtils.toString(clientHttpResponse.getBody());

                        int indexOfMatch = bodyAsText.indexOf(fragPattern);

                        if (indexOfMatch != -1) {
                            String sectionContainingPrice =
                                    bodyAsText.substring(indexOfMatch);
                            Matcher matcher = symbolSize.matcher(sectionContainingPrice);

                            StringBuilder stringBuilder = new StringBuilder();

                            while (matcher.find()) {
                                stringBuilder.append(matcher.group(1))
                                        .append(".").append(matcher.group(2));
                            }

                            String response = stringBuilder.toString();
                            Float fl = Float.parseFloat(response);

                            return new Stock(symbol, fl);
                        }

                        return null;
                    }
                });

        return stock;
    }

    @Override
    public Message<Stock> receive() {
        Stock stock = getStockInformationFor(this.tickerSymbol);

        if (stock == null) {
            return null;
        }

        return MessageBuilder.withPayload(stock).setHeader("symbol", this.tickerSymbol)
                .setHeader("price", stock.getPrice()).build();
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.tickerSymbol = this.tickerSymbol.trim().toUpperCase();
    }
}
