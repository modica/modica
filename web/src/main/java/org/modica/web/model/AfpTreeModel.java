package org.modica.web.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class AfpTreeModel extends LoadableDetachableModel<SfTreeNode> {

    @SpringBean
    private AfpService afpService;

    private static final long serialVersionUID = 1L;

    public AfpTreeModel() {
        Injector.get().inject(this);
    }

    @Override
    protected SfTreeNode load() {
        return afpService.getSfTreeNode();
    }

}
