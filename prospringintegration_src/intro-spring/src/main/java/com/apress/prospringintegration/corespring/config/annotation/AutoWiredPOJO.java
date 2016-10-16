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

package com.apress.prospringintegration.corespring.config.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public class AutoWiredPOJO {

    public ColorEnum color;

    @Autowired
    public ColorRandomizer colorRandomizer;

    @Autowired(required = false)
    public ColorPicker colorPicker;

    @Autowired
    @Randomizer("noRedColors")
    public ColorRandomizer noRedRandomColors;

    public AutoWiredPOJO() {
    }

    public AutoWiredPOJO(ColorEnum color) {
        this.color = color;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    @Required
    public ColorRandomizer getColorRandomizer() {
        return colorRandomizer;
    }

    public void setColorRandomizer(ColorRandomizer colorRandomizer) {
        this.colorRandomizer = colorRandomizer;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }
}
