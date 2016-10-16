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

package com.apress.prospringintegration.monitoring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MonitoringExample {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:monitoring/monitoring.xml");

        ProcessMessage processMessage = context.getBean("processMessage", ProcessMessage.class);
        for (int i = 0; i < 10; i++) {
            processMessage.processMessage("Process");
            processMessage.checkMessage("Check");
        }

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                //do nothing
            }
            context.stop();
        }
    }
}
