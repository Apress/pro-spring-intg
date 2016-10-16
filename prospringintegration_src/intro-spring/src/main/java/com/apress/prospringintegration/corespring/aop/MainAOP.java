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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainAOP {
    public static void main(String[] args) throws Exception {

        ApplicationContext app = new ClassPathXmlApplicationContext("ioc_aop.xml");
        PurchaseOrderProcessor orderProcessor =
                app.getBean(PurchaseOrderProcessor.class);

        PurchaseOrder order = new PurchaseOrder();
        order.setItemCost(1000.00f);
        Receipt receipt = orderProcessor.processPurchaseOrder(order);

        PurchaseOrderDiscountProcessor orderDiscountProcessor =
                (PurchaseOrderDiscountProcessor) orderProcessor;
        Receipt discountedReceipt =
                orderDiscountProcessor.processDiscountOrder(order,
                        DiscountStrategy.HALF_OFF_ENTIRE);

        System.out.println(
                String.format("Total discounted purchase amount (given %s discount): %f ",
                        DiscountStrategy.HALF_OFF_ENTIRE,
                        (discountedReceipt.getPurchaseAmt() -
                                discountedReceipt.getDiscountedAmount())));
    }
}
