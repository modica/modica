package org.afpparser.web.model;

import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.StructuredField;

/**
 * A node in the structured field tree.
 */
public class SfTreeNode {

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

    @Override
    public String toString() {
        return field.toString();
    }
}
