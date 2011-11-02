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

public class PrintingSFHandler implements StructuredFieldHandler {

    private final PrintStream out;

    private String indent = "";

    public PrintingSFHandler(PrintStream out) {
        this.out = out;
    }

    public PrintingSFHandler() {
        this(System.out);
    }

    public void handle(SfIntroducer sf) {
        out.println(indent + sf);
    }

    public void handleBegin(SfIntroducer sf) {
        out.println(indent + sf);
        indent += "  ";
    }

    public void handleEnd(SfIntroducer sf) {
        indent = indent.substring(0, indent.length() - 2);
        out.println(indent + sf);
    }
}
