/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id:$ */

package org.afpparser.parser;

import java.io.PrintStream;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.common.StringUtils;

/**
 * This class is used for printing SFIntroducerHandler events to a PrintStream.
 */
public class PrintingSFIntoducerHandler implements SFIntroducerHandler {

    private final PrintStream out;

    private String indent = "  ";

    private PrintingSFIntoducerHandler(PrintStream out) {
        this.out = out;
    }

    private PrintingSFIntoducerHandler() {
        this(System.out);
    }

    @Override
    public void handle(SfIntroducer sf) {
        printSf(sf);
    }

    @Override
    public void handleBegin(SfIntroducer sf) {
        printSf(sf);
        indent += "  ";
    }

    @Override
    public void handleEnd(SfIntroducer sf) {
        indent = indent.substring(0, indent.length() - 2);
        printSf(sf);
    }

    @Override
    public void startAfp() {
    }

    @Override
    public void endAfp() {
    }

    private void printSf(SfIntroducer sf) {
        out.println("\u001B[34m" + StringUtils.toHex(sf.getOffset(), 8) + "\u001B[0m" + indent
                + sf.getType().getName());
    }

    /**
     * Return a new instance of PrintingSFHandler.
     * 
     * @return
     */
    public static SFIntroducerHandler newInstance() {
        return new PrintingSFIntoducerHandler();
    }

    /**
     * Return a new instance of PrintingSFHandler.
     * 
     * @param out
     *            OutputStream to print to.
     * @return
     */
    public static SFIntroducerHandler newInstance(PrintStream out) {
        return new PrintingSFIntoducerHandler(out);
    }
}
