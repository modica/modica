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

package org.modica.afp.modca;

import java.util.HashMap;
import java.util.Map;

public class EbcdicStringHandler {

    private static final Map<Integer, String> codePageMappings = new HashMap<Integer, String>();
    public static final int DEFAULT_CPGID = 500;
    public static final String DEFAULT_CPGID_NAME = "Cp500";

    static {
        codePageMappings.put(500, "Cp500");
        codePageMappings.put(1208, "UTF-8");
        codePageMappings.put(1200, "UTF-16BE");
        codePageMappings.put(367, "US-ASCII");
    }

    public static String getCodePage(int gpgid) {
        return checkNotNull(codePageMappings.get(gpgid));
    }

    private static String checkNotNull(String codePage) {
        return codePage != null ? codePage : codePageMappings.get(DEFAULT_CPGID);
    }
}
