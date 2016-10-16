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

package com.apress.prospringintegration.customadapters.inbound.eventdriven;

import com.apress.prospringintegration.customadapters.inbound.eventdriven.fsmon.DirectoryMonitor;
import com.apress.prospringintegration.customadapters.inbound.eventdriven.fsmon.LinuxInotifyDirectoryMonitor;
import org.apache.commons.lang.SystemUtils;
import org.springframework.integration.Message;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

import java.io.File;
import java.util.concurrent.Executor;

public class DirectoryMonitorInboundFileEndpoint extends MessageProducerSupport {

    private File directoryToMonitor;
    private DirectoryMonitor monitor;
    private Executor executor;
    private int queueSize = 10;

    @Override
    protected void onInit() {
        try {
            if (SystemUtils.IS_OS_LINUX) {
                LinuxInotifyDirectoryMonitor mon = new LinuxInotifyDirectoryMonitor();
                if (executor != null) {
                    mon.setExecutor(executor);
                }
                mon.afterPropertiesSet();
                this.monitor = mon;
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception thrown when trying to setup "
                    + DirectoryMonitorInboundFileEndpoint.class, e);
        }
    }

    @Override
    protected void doStart() {
        Assert.notNull(monitor, "the monitor can't be null");
        MessageProducingFileAddedListener messageProducingFileAddedListener =
                new MessageProducingFileAddedListener();
        monitor.monitor(directoryToMonitor, messageProducingFileAddedListener);
    }

    @Override
    protected void doStop() {
    }

    public void setDirectoryToMonitor(File directoryToMonitor) {
        this.directoryToMonitor = directoryToMonitor;
    }

    class MessageProducingFileAddedListener
            implements DirectoryMonitor.FileAddedListener {
        @Override
        public void fileAdded(File dir, String fn) {
            File fi = new File(dir, fn);
            Message<File> msg =
                    MessageBuilder.withPayload(fi).setHeader(
                            FileHeaders.FILENAME, fi.getPath()).build();
            sendMessage(msg);
        }
    }
}
