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

package com.apress.prospringintegration.corespring.iocbasics;

import java.util.ArrayDeque;
import java.util.Deque;

public class ColorQueue {

    ColorRandomizer colorRandomizer;

    public void setColorRandomizer(ColorRandomizer c) {
        this.colorRandomizer = c;
    }

    public void verifyPropertiesSet() throws Exception {
        if (colorRandomizer == null || wagers == null || (!wagers.isEmpty()))
            throw new Exception("ColorQueue in non-initial state.");
    }

    Deque<Wager> wagers = new ArrayDeque<Wager>();

    public void placeWager(ColorEnum c, float amt, String name) {
        wagers.push(new Wager(c, amt, name));
    }

    void pickWinners() {
        while (!wagers.isEmpty()) {
            Wager thisWager = wagers.pop();
            ColorEnum randomColor = colorRandomizer.randomColor();
            if (randomColor == thisWager.getColor())
                System.out.println("Winning:" + thisWager.getName() + " waged " + thisWager.getAmt() + " for " +
                        thisWager.getColor());
            else
                System.out.println("No winners for :" + randomColor);
        }
    }

    void clear() {
        wagers.clear();
    }
}
