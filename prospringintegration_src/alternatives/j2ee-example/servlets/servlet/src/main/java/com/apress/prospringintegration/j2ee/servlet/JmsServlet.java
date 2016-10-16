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

package com.apress.prospringintegration.j2ee.servlet;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JmsServlet extends HttpServlet {

    private static final String QUEUE_CONNECTION_FACTORY = "ConnectionFactory";
    private static final String EXAMPLE_QUEUE = "queue/examples/ExampleQueue";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet JmsServlet");
        String message = request.getParameter("message");
        System.out.println("message: " + message);
        try {
            Connection connection = getConnectionFactory().createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(getDestination());
            Message jmsMessage = session.createTextMessage(message);
            messageProducer.send(jmsMessage);
            System.out.println("Message sent to " + EXAMPLE_QUEUE);
            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        request.setAttribute("started", "true");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory jmsConnectionFactory = null;
        try {
            Context ctx = new InitialContext();
            jmsConnectionFactory = (ConnectionFactory) ctx.lookup(QUEUE_CONNECTION_FACTORY);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return jmsConnectionFactory;
    }

    private Destination getDestination() {
        Destination jmsDestination = null;
        try {
            Context ctx = new InitialContext();
            jmsDestination = (Destination) ctx.lookup(EXAMPLE_QUEUE);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return jmsDestination;
    }
}
