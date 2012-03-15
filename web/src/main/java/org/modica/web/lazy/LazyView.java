package org.modica.web.lazy;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.modica.web.model.AfpTreeModel;
import org.modica.web.model.SfTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyView extends Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(LazyViewPanel.class);

    private final AfpTreeModel afpTreeModel;

    public LazyView(String id, AfpTreeModel afpTreeModel) {
        super(id);
        this.afpTreeModel = afpTreeModel;
        ListView<SfTreeNode> lv = new PropertyListView<SfTreeNode>("fields", new ListModel()) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<SfTreeNode> item) {
                SfTreeNode node = item.getModelObject();
                String name = node.getField() == null ? "ROOT" : node.getField().toString();
                item.add(new Label("sf_string", name));

            }
        };
        add(lv);
    }

    public void update() {
        LOG.debug("update()");
    }

    private class ListModel extends LoadableDetachableModel<List<SfTreeNode>> {
        private static final long serialVersionUID = 1L;

        @Override
        protected List<SfTreeNode> load() {
            SfTreeNode root = afpTreeModel.getObject();
            return root == null ? Collections.<SfTreeNode> emptyList() : root.getAll();
        }
    }

}
