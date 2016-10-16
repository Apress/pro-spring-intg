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

import org.springframework.stereotype.Component;

@Component
public class RandomItemGenerator {

    String[] itemNames =
            {"desk", "couch", "monitor", "clock", "EIP Book", "keyboard", "chair", "cellphone", "mouse", "harddrive",
                    "coffee"};

    public Item getRandomItem() {
        Item item = new Item();
        item.setCost((float) Math.random() * 250);
        item.setDescription("random item");

        int inum = (int) Math.round(Math.random() * (itemNames.length - 1));
        item.setId(inum);
        item.setName(itemNames[inum]);

        return item;
    }

    ;
}
