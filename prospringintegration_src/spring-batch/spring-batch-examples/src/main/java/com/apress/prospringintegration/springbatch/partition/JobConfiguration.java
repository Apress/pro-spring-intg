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

package com.apress.prospringintegration.springbatch.partition;

import com.apress.prospringintegration.springbatch.UserRegistration;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    @Scope("step")
    public FlatFileItemReader dataReader(
            @Value("file:src/main/resources/sample/#{jobParameters['input.file']}.csv")
            Resource resource) {
        FlatFileItemReader csvFileReader = new FlatFileItemReader();
        csvFileReader.setResource(resource);

        DelimitedLineTokenizer delimitedLineTokenizer =
                new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA);
        delimitedLineTokenizer.setNames(
                new String[]{"firstName", "lastName", "company", "address", "city",
                        "state", "zip", "county", "url", "phoneNumber", "fax"});

        BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
        beanWrapperFieldSetMapper.setTargetType(UserRegistration.class);

        DefaultLineMapper defaultLineMapper = new DefaultLineMapper();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        csvFileReader.setLineMapper(defaultLineMapper);

        return csvFileReader;
    }

    @Bean
    public JdbcBatchItemWriter dataWriter() {
        JdbcBatchItemWriter jdbcBatchItemWriter = new JdbcBatchItemWriter();
        jdbcBatchItemWriter.setAssertUpdates(true);
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter
                .setSql("insert into USER_REGISTRATION(FIRST_NAME, LAST_NAME, COMPANY," +
                        "ADDRESS, CITY, STATE, ZIP, COUNTY, URL, PHONE_NUMBER, FAX )" +
                        "values (:firstName, :lastName, :company, :address, :city ," +
                        ":state, :zip, :county, :url, :phoneNumber, :fax )");

        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider());

        return jdbcBatchItemWriter;
    }

}
