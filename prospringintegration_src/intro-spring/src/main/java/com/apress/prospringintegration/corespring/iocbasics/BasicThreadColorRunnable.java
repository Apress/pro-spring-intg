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

package com.apress.prospringintegration.corespring.iocbasics;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BasicThreadColorRunnable implements Runnable {
    ApplicationContext ctx;

    public BasicThreadColorRunnable(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public final void run() {
        try {
            ColorEnum color = ctx.getBean("threadColor", ColorEnum.class);
            System.out.println("Child Thread color: " + color);
        } finally {
            // do nothing
        }
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext app = new ClassPathXmlApplicationContext("ioc_basics.xml");
        ColorEnum threadColor = app.getBean("threadColor", ColorEnum.class);
        System.out.println("Parent thread color: " + threadColor);

        BasicThreadColorRunnable dcr = new BasicThreadColorRunnable(app);
        new Thread(dcr).start();
    }
}
