package org.modica.web.model;

import java.io.Serializable;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.common.StringUtils;

public class SfModelBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String col1;
    private final String col2;
    private final String col3;

    public SfModelBean(StructuredField intro) {
        this.col1 = StringUtils.toHex(intro.getOffset(), 8);
        this.col2 = intro.getType().getName();
        this.col3 = "";
    }

    public SfModelBean(String col1, String col2) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = "";
    }

    public SfModelBean(String col1, String col2, String col3) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
    }

    public String getColumn1() {
        return col1;
    }

    public String getColumn2() {
        return col2;
    }

    public String getColumn3() {
        return col3;
    }

    @Override
    public String toString() {
        return getColumn1() + ", " + getColumn2();
    }
}
