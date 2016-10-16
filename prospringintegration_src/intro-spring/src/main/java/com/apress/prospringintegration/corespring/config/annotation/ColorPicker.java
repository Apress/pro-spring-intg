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
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class ColorPicker {

    @Resource(name = "colorRandomizer")
    ColorRandomizer namedColorRandomizer;

    @Autowired(required = true)
    ColorRandomizer colorRandomizer;

    @Autowired
    Map<String, ColorRandomizer> colorRandomizerMap;

    @Autowired
    List<ColorRandomizer> colorRandomizers;

    @Autowired
    Map<String, ColorEnum> colors;

    @Autowired
    @Qualifier("noRedColors")
    public ColorRandomizer noRedRandomColors;

    public ColorRandomizer getColorRandomizer() {
        return colorRandomizer;
    }

    public void setColorRandomizer(ColorRandomizer colorRandomizer) {
        this.colorRandomizer = colorRandomizer;
    }
}
