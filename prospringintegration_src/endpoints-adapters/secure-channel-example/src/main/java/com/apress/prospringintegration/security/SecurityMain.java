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

package com.apress.prospringintegration.security;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.CollectionUtils;

public class SecurityMain {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:secure-channel.xml");
        context.start();

        MessageChannel channel =
                context.getBean("secureCustomerData", MessageChannel.class);

        //Secure user with privileges
        login("secureuser", "password", "ROLE_ADMIN");
        try {
            send(channel, "hello secure world!");
        } catch (Exception ex) {
            System.out.println("Unable to send message for secureuser");
        }

        //Secure user with privileges
        login("unsecureuser", "password", "ROLE_USER");
        try {
            send(channel, "hello secure world!");
        } catch (Exception ex) {
            System.out.println("Unable to send message for unsecureuser");
            ex.printStackTrace();
        }

    }

    public static void login(String username, String password, String... roles) {
        SecurityContext context = createContext(username, password, roles);
        SecurityContextHolder.setContext(context);
    }

    public static void send(MessageChannel channel, String message) {
        channel.send(MessageBuilder.withPayload(message).build());
    }

    @SuppressWarnings("unchecked")
    public static SecurityContext createContext(String username,
                                                String password,
                                                String... roles) {
        SecurityContextImpl ctxImpl = new SecurityContextImpl();
        UsernamePasswordAuthenticationToken authToken;
        if (roles != null && roles.length > 0) {
            GrantedAuthority[] authorities = new GrantedAuthority[roles.length];
            for (int i = 0; i < roles.length; i++) {
                authorities[i] = new GrantedAuthorityImpl(roles[i]);
            }
            authToken = new UsernamePasswordAuthenticationToken(username, password,
                    CollectionUtils.arrayToList(authorities));
        } else {
            authToken = new UsernamePasswordAuthenticationToken(username, password);
        }
        ctxImpl.setAuthentication(authToken);
        return ctxImpl;
    }
}
