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

package com.apress.prospringintegration.customadapters.inbound.eventdriven.fsmon;

import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DirectoryMonitorClient {
    public static void main(String[] args) throws Throwable {
        File[] files = {
                new File(new File(SystemUtils.getUserHome(), "Desktop"), "test2"),
                new File(new File(SystemUtils.getUserHome(), "Desktop"), "test1")};

        Executor ex = Executors.newFixedThreadPool(10);

        final LinuxInotifyDirectoryMonitor monitor = new LinuxInotifyDirectoryMonitor();
        monitor.setExecutor(ex);
        monitor.afterPropertiesSet();

        final DirectoryMonitor.FileAddedListener fileAddedListener =
                new DirectoryMonitor.FileAddedListener() {
                    @Override
                    public void fileAdded(File dir, String fn) {
                        System.out.println("A new file in " + dir.getAbsolutePath()
                                + " called " + fn + " has been noticed");
                    }
                };

        for (File f : files) {
            monitor.monitor(f, fileAddedListener);
        }
    }
}
