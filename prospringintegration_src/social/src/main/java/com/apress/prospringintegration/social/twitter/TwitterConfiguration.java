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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.twitter.core.Twitter4jTemplate;
import org.springframework.integration.twitter.core.TwitterOperations;

@Configuration
public class TwitterConfiguration {

    @Value("${consumer-key}")
    private String consumerKey;

    @Value("${consumer-secret}")
    private String consumerSecret;

    @Value("${access-token}")
    private String accessToken;

    @Value("${access-token-secret}")
    private String accessTokenSecret;

    @Bean
    public TwitterOperations twitterTemplate() {
        Twitter4jTemplate twitterOperations =
                new Twitter4jTemplate(
                        consumerKey, consumerSecret, accessToken, accessTokenSecret);
        return twitterOperations;
    }
}
