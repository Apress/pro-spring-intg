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
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.twitter.core.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TwitterMessageConsumer {
    private static Logger LOG = Logger.getLogger(TwitterMessageConsumer.class);

    @ServiceActivator
    public void consume(Message<Tweet> message) {
        Tweet tweet = message.getPayload();
        LOG.info(tweet.getText() + " from: " + tweet.getFromUser());
    }
}
