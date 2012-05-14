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
