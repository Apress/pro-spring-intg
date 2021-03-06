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

package com.apress.prospringintegration.social.twitter;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TwitterMentionInbound {
    private static Logger LOG = Logger.getLogger(TwitterMentionInbound.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "/spring/twitter/twitter-mention-inbound.xml");

        Thread.sleep(10 * 60 * 1000);
    }
}
