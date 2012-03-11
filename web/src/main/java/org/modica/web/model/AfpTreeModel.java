package org.modica.web.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class AfpTreeModel extends Model<SfTreeNode> {

    @SpringBean
    private AfpService afpService;

    private static final long serialVersionUID = 1L;

    public AfpTreeModel() {
        Injector.get().inject(this);
    }

    @Override
    public SfTreeNode getObject() {
        return afpService.getSfTreeNode();
    }

    @Override
    public void setObject(SfTreeNode object) {
        throw new UnsupportedOperationException();
    }
}
