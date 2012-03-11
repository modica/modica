package org.modica.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;

/**
 * A node in the structured field tree.
 */
public class SfTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Object> sfList;

    private final StructuredField field;

    public SfTreeNode(StructuredField structuredField) {
        sfList = new ArrayList<Object>();
        this.field = structuredField;
    }

    public void addChild(SfTreeNode treeNode) {
        sfList.add(treeNode);
    }

    public void addChild(StructuredField structuredField) {
        sfList.add(structuredField);
    }

    public StructuredField getField() {
        return field;
    }

    public List<Object> getChilden() {
        return sfList;
    }

}
