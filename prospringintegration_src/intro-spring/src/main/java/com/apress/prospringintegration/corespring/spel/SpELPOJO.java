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

package com.apress.prospringintegration.corespring.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SpELPOJO {

    @Value("#{systemProperties}")
    private Properties systemProperties;

    @Value("#{systemProperties['user.region']}")
    private String userRegion;

    @Value("#{ T(java.lang.Math).random() * 100.0}")
    private double randomNumber;

    @Value("#{emailUtilities.email}")
    private String email;

    @Value("#{emailUtilities.password}")
    private String password;

    @Value("#{emailUtilities.host}")
    private String host;

    @Value("#{emailUtilities}")
    private EmailUtilities emailUtilities;
}
