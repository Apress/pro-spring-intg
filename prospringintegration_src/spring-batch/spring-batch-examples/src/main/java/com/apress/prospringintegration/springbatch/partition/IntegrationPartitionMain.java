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

import org.springframework.batch.core.*;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;

import java.util.Date;

public class IntegrationPartitionMain {

    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "message-partition.xml");
        context.start();

        MessageChannel launchChannel = context.getBean("launchChannel", MessageChannel.class);
        QueueChannel statusChannel = context.getBean("statusChannel", QueueChannel.class);

        Job job = (Job) context.getBean("importData");
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("date", new Date());
        jobParametersBuilder.addString("input.file", "registrations");
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        JobLaunchRequest jobLaunchRequest = new JobLaunchRequest(job, jobParameters);

        launchChannel.send(MessageBuilder.withPayload(jobLaunchRequest).build());

        Message<JobExecution> statusMessage = (Message<JobExecution>) statusChannel.receive();
        JobExecution jobExecution = statusMessage.getPayload();
        
        System.out.println(jobExecution);
        System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());

        JobInstance jobInstance = jobExecution.getJobInstance();
        System.out.println("job instance Id: " + jobInstance.getId());
    }
}
