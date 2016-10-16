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
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@org.springframework.core.annotation.Order(1)
@Component
public class PurchaseOrderProcessorAspect {

    private Log log = LogFactory.getLog(this.getClass());

    @Before("execution(* com.apress.prospringintegration.corespring.aop.PurchaseOrderProcessor.processPurchaseOrder(..))")
    public void logBefore() {
        log.info("The PurchaseOrder is being processed");
    }

    @After("execution(* com.apress.prospringintegration.corespring.aop.PurchaseOrderProcessor.processPurchaseOrder(..))")
    public void logAfter(JoinPoint joinPoint) {

        PurchaseOrder purchaseOrder = (PurchaseOrder) joinPoint.getArgs()[0];
        log.info("The PurchaseOrder was processed at: " + purchaseOrder.getProcessedTime());
    }

    @AfterReturning(
            pointcut = "execution(* com.apress.prospringintegration.corespring.aop.PurchaseOrderProcessor.processPurchaseOrder(..))",
            returning = "result")
    public void adviceAfterReturning(JoinPoint joinPoint, Object result) {

        Receipt receipt = (Receipt) result;
        log.info("The receipt value is:" + receipt.getAuthcode());
    }

    @AfterThrowing(throwing = "e",
            pointcut = "execution(* *.process*(..))")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getSignature().getName();
        log.error("An error " + e + " was thrown in " + methodName);
    }

    @Around("execution(* com.apress.prospringintegration.corespring.aop.PurchaseOrderProcessor.processPurchaseOrder(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info(" Method: " + methodName);
        try {
            Object result = joinPoint.proceed();
            log.info(" Method: " + methodName + "returns " + result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error(e);
            throw e;
        }
    }
}
