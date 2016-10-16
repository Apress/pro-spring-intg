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

package com.apress.prospringintegration.corespring.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Aspect
@Component
public class PurchaseOrderProcessorStatsAspect implements Ordered {
    public int getOrder() {
        return 0;
    }

    private Log log = LogFactory.getLog(this.getClass());

    @Around("execution(* com.apress.prospringintegration.corespring.aop.PurchaseOrderProcessor.processPurchaseOrder(..))")
    public Object aroundStatsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String classPackage = joinPoint.getSignature().getClass().getPackage().getName();

        String fullCall = classPackage + "." + methodName;
        try {
            long tStart = Calendar.getInstance().getTimeInMillis();
            Object result = joinPoint.proceed();
            long tEnd = Calendar.getInstance().getTimeInMillis();

            log.info(" Method: " + fullCall + " took " + (tEnd - tStart) + " miliseconds");
            return result;
        } catch (IllegalArgumentException e) {
            log.error(e);
            throw e;
        }
    }
}
