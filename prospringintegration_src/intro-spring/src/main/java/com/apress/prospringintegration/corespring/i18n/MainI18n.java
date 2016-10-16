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

package com.apress.prospringintegration.corespring.i18n;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.Locale;

public class MainI18n {

    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("ioc_resource_bundles.xml");

        String qMessage = ctx.getMessage("queued", null, Locale.US);
        System.out.println(qMessage);

        qMessage = ctx.getMessage("queued", null, Locale.UK);
        System.out.println(qMessage);

        String filename = "ProSpringIntegration.xml";
        MessageSourceResolvable processed =
                new DefaultMessageSourceResolvable(new String[]{"complete"},
                        new String[]{filename}, " Your data has been processed!");
        String msrQmessage = ctx.getMessage(processed, Locale.FRANCE);
        System.out.println(msrQmessage);

    }
}
