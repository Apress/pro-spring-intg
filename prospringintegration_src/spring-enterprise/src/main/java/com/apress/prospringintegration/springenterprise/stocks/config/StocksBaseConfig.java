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

package com.apress.prospringintegration.springenterprise.stocks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class StocksBaseConfig {

    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${jdbc.username}")
    String username;

    @Value("${jdbc.password}")
    String password;

    @Value("#{h2DataSource}")
    DataSource embeddedDataSource;

    @Bean
    public DataSource dataSource() {
        return this.embeddedDataSource;
    }

    // Remote Data Source ( just for illustrating remote connection )
    @Bean
    public DataSource remoteDataSource() {
        return new DriverManagerDataSource(jdbcUrl, username, password);
    }
}
