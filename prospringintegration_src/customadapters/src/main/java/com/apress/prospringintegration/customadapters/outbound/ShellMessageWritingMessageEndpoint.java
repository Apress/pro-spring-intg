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

package com.apress.prospringintegration.customadapters.outbound;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.util.Assert;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShellMessageWritingMessageEndpoint extends AbstractMessageHandler {

    static public String USERID_HEADER = "shell-userid";

    static public String TERMINALS_HEADER = "shell-terminal";

    protected void write(String user, String msg, String terminal) {
        List<String> cmdList = new ArrayList<String>();
        cmdList.add("write");
        cmdList.add(user);

        if (!StringUtils.isEmpty(terminal)) {
            cmdList.add(terminal);
        }

        writeToShellCommand(cmdList.toArray(new String[cmdList.size()]), msg);
    }

    protected void wall(String msg) {
        writeToShellCommand(new String[]{"wall"}, msg);
    }

    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception {

        Assert.isInstanceOf(String.class, message.getPayload(),
                "the payload must be a String");

        String msg = (String) message.getPayload();

        MessageHeaders headers = message.getHeaders();

        try {
            if (headers.containsKey(USERID_HEADER)) {
                String ptys = headers.containsKey(TERMINALS_HEADER) ?
                        (String) headers.get(TERMINALS_HEADER) : null;
                String userid = (String) headers.get(USERID_HEADER);
                write(userid, msg, ptys);
            } else {
                wall(msg);
            }
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    protected int writeToShellCommand(String[] cmds, String msg) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList(cmds));
            Process proc = processBuilder.start();

            Writer streamWriter = null;

            try {
                streamWriter = new OutputStreamWriter(proc.getOutputStream());
                streamWriter.write(msg);
            } finally {
                IOUtils.closeQuietly(streamWriter);
            }

            int retVal = proc.waitFor();

            if (retVal != 0) {
                throw new RuntimeException("couldn't write message to 'write'");
            }

            return retVal;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
