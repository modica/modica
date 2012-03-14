package org.modica.web.model;

import java.io.Serializable;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;

public interface SfTreeNode extends Serializable {

    StructuredField getField();

    List<SfTreeNodeImpl> getChilden();
}
