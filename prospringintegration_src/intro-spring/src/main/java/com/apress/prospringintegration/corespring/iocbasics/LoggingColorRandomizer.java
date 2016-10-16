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

package com.apress.prospringintegration.corespring.iocbasics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggingColorRandomizer extends ColorRandomizer {
    File logFile;
    FileWriter writer;

    public void setLogFile(File f) {
        this.logFile = f;
    }

    void writeFileLine(String str) {
        try {
            writer.write(str + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        writer = new FileWriter(logFile, true);
        writeFileLine("initialized.");
    }

    @Override
    public ColorEnum randomColor() {
        ColorEnum col = super.randomColor();
        writeFileLine("randomColor: " + col);
        return col;
    }

    @Override
    public ColorEnum exceptColor(ColorEnum ex) {
        ColorEnum col = super.exceptColor(ex);
        writeFileLine(" exceptColor:" + col);
        return col;
    }

    public void complete() throws IOException {
        writeFileLine("closed.\n");
        writer.close();
    }
}
