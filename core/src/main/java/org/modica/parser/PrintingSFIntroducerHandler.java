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

package org.modica.parser;

import java.io.PrintStream;

import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.common.StringUtils;

import com.google.common.base.Strings;

/**
 * This class is used for printing SFIntroducerHandler events to a PrintStream.
 */
public class PrintingSFIntroducerHandler implements StructuredFieldIntroducerHandler {

    private static final String SF_TMPL = "\u001B[34m%s\u001B[0m  %s%s\n";

    private final PrintStream out;

    private final int indentSize;

    private final String indentShift;

    private String indent = "";

    public PrintingSFIntroducerHandler(PrintStream out) {
        this(out, 2);
    }

    public PrintingSFIntroducerHandler(PrintStream out, int indentSize) {
        this.out = out;
        this.indentSize = indentSize;
        this.indentShift = Strings.repeat(" ", indentSize);
    }

    @Override
    public void handle(StructuredFieldIntroducer sf) {
        printSf(sf);
    }

    @Override
    public void handleBegin(StructuredFieldIntroducer sf) {
        printSf(sf);
        incrementIndent();
    }

    @Override
    public void handleEnd(StructuredFieldIntroducer sf) {
        decrementIndent();
        printSf(sf);
    }

    private void incrementIndent() {
        indent += indentShift;
    }

    private void decrementIndent() {
        indent = indent.substring(0, indent.length() - indentSize);
    }

    @Override
    public void startAfp() {
    }

    @Override
    public void endAfp() {
    }

    private void printSf(StructuredFieldIntroducer sf) {
        out.printf(SF_TMPL, StringUtils.toHex(sf.getOffset(), 8), indent, sf.getType().getName());
    }
}
