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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LinuxInotifyDirectoryMonitor implements InitializingBean, DirectoryMonitor {
    private static Logger logger = Logger.getLogger(LinuxInotifyDirectoryMonitor.class);

    static {
        try {
            System.loadLibrary("sifsmon");
        } catch (Throwable t) {
            logger.error("Received exception " + ExceptionUtils.getFullStackTrace(t)
                    + " when trying to load the native library sifsmon");
        }
    }

    private volatile Executor executor;

    private Map<String, File> mapOfDirectoriesUnderMonitor =
            new ConcurrentHashMap<String, File>();

    private volatile ConcurrentHashMap<File, FileAddedListener> monitors =
            new ConcurrentHashMap<File, FileAddedListener>();

    private boolean autoCreateDirectory = true;

    protected boolean exists(File dir) {
        boolean goodDirToMonitor = (dir.isDirectory() && dir.exists());

        Assert.notNull(dir, "the 'dir' parameter must not be null");

        if (!goodDirToMonitor) {
            if (!dir.exists()) {
                if (this.autoCreateDirectory) {
                    if (!dir.mkdirs()) {
                        logger.debug(String.format("couldn't create directory %s",
                                dir.getAbsolutePath()));
                    }
                }
            }
        }

        Assert.state(dir.exists(), "the directory " + dir.getAbsolutePath()
                + " doesn't exist");

        return dir.exists();
    }

    public void fileReceived(String dir, String fileName) {
        File dirFile = mapOfDirectoriesUnderMonitor.get(dir);
        this.monitors.get(dirFile).fileAdded(dirFile, fileName);
    }

    @Override
    public void monitor(final File dir, final FileAddedListener fal) {
        if (exists(dir)) {
            mapOfDirectoriesUnderMonitor.put(dir.getAbsolutePath(), dir);
            monitors.putIfAbsent(dir, fal);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    monitor(dir.getAbsolutePath());
                }
            });
        }
    }

    native void monitor(String path);

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.executor == null) {
            this.executor = Executors.newFixedThreadPool(10);
        }
    }
}
