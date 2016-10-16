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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.timer.TimerTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TaskExecutorExample {
    @Autowired
    private SimpleAsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    private SyncTaskExecutor syncTaskExecutor;

    @Autowired
    private TaskExecutorAdapter taskExecutorAdapter;

    /*  No need, since the scheduling is already configured, in the application context
    @Resource(name = "timerTaskExecutorWithScheduledTimerTasks")
    private TimerTaskExecutor timerTaskExecutorWithScheduledTimerTasks;
    */

    @Resource(name = "timerTaskExecutorWithoutScheduledTimerTasks")
    private TimerTaskExecutor timerTaskExecutorWithoutScheduledTimerTasks;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private DemonstrationRunnable task;

    public void submitJobs() {
        syncTaskExecutor.execute(task);
        taskExecutorAdapter.submit(task);
        asyncTaskExecutor.submit(task);

        timerTaskExecutorWithoutScheduledTimerTasks.submit(task);

        /* will do 100 at a time,
            then queue the rest, ie,
            should take round 5 seconds total
        */
        for (int i = 0; i < 500; i++)
            threadPoolTaskExecutor.submit(task);
    }
}

