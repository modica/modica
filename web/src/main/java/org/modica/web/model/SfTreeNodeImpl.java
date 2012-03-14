package org.modica.web.model;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;

/**
 * A node in the structured field tree.
 */
public class SfTreeNodeImpl implements SfTreeNode {

    private static final long serialVersionUID = 1L;

    private final List<SfTreeNodeImpl> sfList;

    private final StructuredField field;

    SfTreeNodeImpl(StructuredField structuredField) {
        sfList = new ArrayList<SfTreeNodeImpl>();
        this.field = structuredField;
    }

    void addChild(SfTreeNodeImpl treeNode) {
        sfList.add(treeNode);
    }

    @Override
    public StructuredField getField() {
        return field;
    }

    @Override
    public List<SfTreeNodeImpl> getChilden() {
        return sfList;
    }
}
