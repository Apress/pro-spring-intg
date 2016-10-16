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

package com.apress.prospringintegration.messageflow.workflow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class WorkflowMainClient {

    static private Log log = LogFactory.getLog(WorkflowMainClient.class);

    static void deployProcessDefinitions(ProcessEngine processEngine,
                                         String... processDefinitionNames)
            throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        for (String processDefinitionName : processDefinitionNames) {
            DeploymentBuilder deployment = repositoryService.createDeployment()
                    .addClasspathResource(processDefinitionName);
            deployment.deploy();
        }
    }

    public static void main(String[] ars) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("workflow-gateway.xml");
        classPathXmlApplicationContext.start();

        ProcessEngine processEngine =
                classPathXmlApplicationContext.getBean(ProcessEngine.class);

        deployProcessDefinitions(processEngine,
                "processes/hello.bpmn20.xml", "processes/gateway.bpmn20.xml");

        Map<String, Object> processVariables = new HashMap<String, Object>();
        processVariables.put("customerId", 2);

        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey("sigateway", processVariables);

        log.debug("the process instance has been started: PI ID # " + pi.getId());

        Thread.sleep(1000 * 20);
        log.debug("waited 20s");
    }
}
