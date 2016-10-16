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

package com.apress.prospringintegration.corespring.config.componentscan.javaconfig;

import com.apress.prospringintegration.corespring.config.componentscan.ColorEnum;
import com.apress.prospringintegration.corespring.config.componentscan.ColorRandomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainJavaConfig {
    public static void main(String[] args) throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("ioc_java_config.xml");
        ColorRandomizer cr =
                context.getBean("theOnlyColorRandomizer", ColorRandomizer.class);
        System.out.println(cr.randomColor());

        for (int i = 0; i < 5; i++)
            System.out.println("randomcolor: " +
                    context.getBean("randomColor", ColorEnum.class));
    }
}
