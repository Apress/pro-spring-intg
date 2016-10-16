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

package com.apress.prospringintegration.messageflow.workflow;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoggingServiceActivator {

    @Value("${logging-delay}")
    private long delay;

    private Log log = LogFactory.getLog(getClass());

    public Message<?> log(Message<?> msg) throws Exception {

        log.debug("===========================================");
        for (String m : msg.getHeaders().keySet())
            log.debug(String.format("%s = %s", m, msg.getHeaders().get(m)));

        log.debug(ToStringBuilder.reflectionToString(msg));

        log.debug("about to wait for " + this.delay + " ms");

        Thread.sleep(this.delay);

        log.debug("finished waiting. Returning");

        return MessageBuilder.withPayload("All clear!")
                .copyHeadersIfAbsent(msg.getHeaders()).build();
    }
}
