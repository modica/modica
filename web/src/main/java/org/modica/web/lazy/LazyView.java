package org.modica.web.lazy;

import java.util.Collections;
import java.util.List;

import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.web.model.AfpTreeModel;
import org.modica.web.model.SfTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.resource.PackageResourceReference;

public class LazyView extends Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(LazyView.class);

    private final AfpTreeModel afpTreeModel;

    public LazyView(String id, AfpTreeModel afpTreeModel) {
        super(id);
        this.afpTreeModel = afpTreeModel;
        ListView<SfTreeNode> lv = new PropertyListView<SfTreeNode>("field", new ListModel()) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<SfTreeNode> item) {
                item.setOutputMarkupPlaceholderTag(true);
                item.add(new Collapsed("sf", "collapsed", LazyView.this, item));
            }
        };
        add(new Label("size", new SizeModel()));
        add(lv);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference(new PackageResourceReference(LazyView.class, "LazyView.css"));
    }

    private class ListModel extends LoadableDetachableModel<List<SfTreeNode>> {
        private static final long serialVersionUID = 1L;

        @Override
        protected List<SfTreeNode> load() {
            SfTreeNode root = afpTreeModel.getObject();
            return root == null ? Collections.<SfTreeNode> emptyList() : root.getAll();
        }
    }

    private class SizeModel extends LoadableDetachableModel<String> {
        private static final long serialVersionUID = 1L;

        @Override
        protected String load() {
            SfTreeNode root = afpTreeModel.getObject();
            return root == null ? "0" : String.valueOf(root.getAll().size());
        }
    }

    private static class Collapsed extends Fragment {

        private static final long serialVersionUID = 1L;

        public Collapsed(final String id, final String markupId,
                final MarkupContainer markupProvider, final ListItem<SfTreeNode> item) {
            super(id, markupId, markupProvider);
            SfTreeNode node = item.getModelObject();
            String name = node.getField() == null ? "ROOT" : node.getField().toString();
            AjaxLink<Void> link = new AjaxLink<Void>("link") {

                private static final long serialVersionUID = 1L;

                @Override
                public void onClick(AjaxRequestTarget target) {
                    Fragment expanded = new Expanded(id, "expanded", markupProvider, item);
                    expand(expanded);
                    target.add(item);
                }

            };
            link.add(new Label("sf_string", name));
            add(link);
        }

        private void expand(Fragment expanded) {
            replaceWith(expanded);
        }

    }

    private static class Expanded extends Fragment {

        private static final long serialVersionUID = 1L;

        public Expanded(String id, String markupId, MarkupContainer markupProvider,
                final ListItem<SfTreeNode> item) {
            super(id, markupId, markupProvider);
            SfTreeNode node = item.getModelObject();
            StructuredField sf = node.getField();
            if (sf == null) {
                add(new Label("sf_string", "ROOT"));
                add(new Label("param_count", "-"));
            } else {
                add(new Label("sf_string", sf.toString()));
                String size = sf.getParameters() == null ? "0" : String.valueOf(sf.getParameters()
                        .size());
                add(new Label("param_count", size));
            }
        }

    }

}
