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

package com.apress.prospringintegration.webservice.web;

import com.apress.prospringintegration.webservice.CommonConfiguration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.xml.source.DomSourceFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import java.util.Date;
import java.util.Random;

@Component
public class TicketIssuerEndpoint {

    private String replyTemplate =
            "<TicketResponse xmlns=\"" + CommonConfiguration.NAMESPACE + "\">" +
                    "<ticket>" +
                    "<description>%s</description>" +
                    "<priority>%s</priority>" +
                    "<ticketId>%d</ticketId>" +
                    "<issueDateTime>%tc</issueDateTime>" +
                    "</ticket>" +
                    "</TicketResponse>";

    @ServiceActivator
    public Source handleRequest(DOMSource source)
            throws Exception {

        NodeList nodeList = source.getNode().getChildNodes();
        String description = "";
        String priority = "";

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("priority")) {
                priority = node.getFirstChild().getNodeValue();
            } else if (node.getNodeName().equals("description")) {
                description = node.getFirstChild().getNodeValue();
            }
        }

        // Transfer properties to an XML document
        String xml = String.format(replyTemplate, description, priority,
                new Random().nextLong() * 1000, new Date());

        return new DomSourceFactory().createSource(xml);
    }
}

