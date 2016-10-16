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

package com.apress.prospringintegration.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class MultipartHttpClient {

    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        String uri = "http://localhost:8080/http-adapter-1.0.0/inboundMultipartAdapter.html";
        Resource picture =
                new ClassPathResource("com/apress/prospringintegration/web/test.png");
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("name", "John Smith");
        map.add("picture", picture);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("multipart", "form-data"));
        HttpEntity request = new HttpEntity(map, headers);
        ResponseEntity<?> httpResponse = template.exchange(uri, HttpMethod.POST, request, null);
        System.out.println("Status: " + httpResponse.getStatusCode().name());
    }
}
