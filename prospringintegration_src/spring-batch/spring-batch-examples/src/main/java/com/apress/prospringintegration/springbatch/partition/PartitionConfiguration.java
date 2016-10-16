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

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.core.PollableChannel;

import javax.sql.DataSource;

@Configuration
public class PartitionConfiguration {

    @Autowired
    @Qualifier("requestChannel")
    private MessageChannel messageChannel;

    @Autowired
    @Qualifier("replyChannel")
    private PollableChannel pollableChannel;

    @Autowired
    private DataSource dataSource;

    @Bean
    public MessageChannelPartitionHandler partitionHandler() {
        MessageChannelPartitionHandler partitionHandler =
                new MessageChannelPartitionHandler();
        partitionHandler.setMessagingOperations(messagingTemplate());
        partitionHandler.setReplyChannel(pollableChannel);
        partitionHandler.setStepName("step1");
        partitionHandler.setGridSize(10);
        return partitionHandler;
    }

    @Bean
    public MessagingTemplate messagingTemplate() {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setDefaultChannel(messageChannel);
        return messagingTemplate;
    }

    @Bean
    public SimplePartitioner partitioner() {
        SimplePartitioner simplePartitioner = new SimplePartitioner();
        return simplePartitioner;
    }

    @Bean
    public BeanFactoryStepLocator stepLocator() {
        BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
        return stepLocator;
    }

    @Bean
    public JobExplorerFactoryBean jobExplorer() {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource);
        return jobExplorerFactoryBean;
    }

    @Bean
    public StepExecutionRequestHandler stepExecutionRequestHandler() throws Exception {
        StepExecutionRequestHandler stepExecutionRequestHandler =
                new StepExecutionRequestHandler();
        stepExecutionRequestHandler.setJobExplorer((JobExplorer) jobExplorer().getObject());
        stepExecutionRequestHandler.setStepLocator(stepLocator());
        return stepExecutionRequestHandler;
    }
}
