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

package com.apress.prospringintegration.springenterprise.stocks.dao.hibernate;

import com.apress.prospringintegration.springenterprise.stocks.config.StocksBaseConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

@Configuration
@ImportResource("classpath:hibernate-data-access.xml")
public class StocksHibernateConfig extends StocksBaseConfig {

    @Value("hibernate.cfg.xml")
    private Resource hibernateConfigResource;

    Resource hibernateConfigResource() {
        return hibernateConfigResource;
    }

    // Local Session Factory for getting hibernate connections
    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sb = new LocalSessionFactoryBean();
        sb.setDataSource(dataSource());
        sb.setConfigLocation(hibernateConfigResource());
        return sb;
    }

    // Hibernate Template
    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory().getObject());
    }
}

