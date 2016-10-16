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

package com.apress.prospringintegration.monitoring;

import com.jamonapi.MonitorFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource
public class MonitorMBean {

    @ManagedAttribute
    public String[] getData() {
        String[] header = MonitorFactory.getHeader();
        Object[][] data = MonitorFactory.getData();
        String[] result = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            StringBuffer dataValue = new StringBuffer();
            boolean isFirst = true;
            for (int j = 0; j < header.length; j++) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    dataValue.append(",");
                }
                dataValue.append(header[j]).append(":");
                dataValue.append(data[i][j]);
            }
            result[i] = dataValue.toString();
        }
        return result;
    }
}
