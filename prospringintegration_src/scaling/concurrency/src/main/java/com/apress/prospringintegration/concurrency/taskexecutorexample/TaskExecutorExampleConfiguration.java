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

package com.apress.prospringintegration.concurrency.taskexecutorexample;

import com.apress.prospringintegration.concurrency.DemonstrationRunnable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.timer.ScheduledTimerTask;
import org.springframework.scheduling.timer.TimerFactoryBean;
import org.springframework.scheduling.timer.TimerTaskExecutor;

import java.util.concurrent.Executors;

@Configuration
public class TaskExecutorExampleConfiguration {
    @Bean
    public TaskExecutorExample taskExecutorExample() {
        return new TaskExecutorExample();
    }

    @Bean
    public DemonstrationRunnable demonstrationRunnable() {
        return new DemonstrationRunnable();
    }

    @Bean
    public TaskExecutorAdapter taskExecutorAdapter() {
        return new TaskExecutorAdapter(Executors.newCachedThreadPool());
    }

    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setDaemon(false);
        return simpleAsyncTaskExecutor;

    }

    @Bean(name = "timerTaskExecutorWithoutScheduledTimerTasks")
    public TimerTaskExecutor timerTaskExecutor() {
        TimerTaskExecutor timerTaskExecutor = new TimerTaskExecutor();
        timerTaskExecutor.setDelay(10000);
        return timerTaskExecutor;
    }

    @Bean
    public SyncTaskExecutor syncTaskExecutor() {
        return new SyncTaskExecutor();
    }

    @Bean(name = "timerTaskExecutorWithScheduledTimerTasks")
    public TimerTaskExecutor timerTaskExecutor1() {
        ScheduledTimerTask scheduledTimerTask = new ScheduledTimerTask();
        scheduledTimerTask.setDelay(10);
        scheduledTimerTask.setFixedRate(true);
        scheduledTimerTask.setPeriod(10000);
        scheduledTimerTask.setRunnable(this.demonstrationRunnable());

        TimerFactoryBean timerFactoryBean = new TimerFactoryBean();
        timerFactoryBean.setScheduledTimerTasks(new ScheduledTimerTask[]{scheduledTimerTask});
        timerFactoryBean.afterPropertiesSet();
        timerFactoryBean.setBeanName("timerFactoryBean");

        return new TimerTaskExecutor(timerFactoryBean.getObject());
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setDaemon(false);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setMaxPoolSize(100);
        executor.setAllowCoreThreadTimeOut(true);
        return executor;
    }
}
