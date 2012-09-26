package org.modica.web.treeview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.common.StringUtils;
import org.modica.web.model.AfpTreeModel;
import org.modica.web.model.SfTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.tree.PropertyTreeColumn;
import com.inmethod.grid.treegrid.TreeGrid;

public class TreeView extends Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(TreeViewPanel.class);

    private final AfpTreeModel afpTreeModel;

    private final DefaultMutableTreeNode rootNode;

    private final DefaultTreeModel treeModel;

    private final TreeGrid<DefaultTreeModel, DefaultMutableTreeNode> tree;

    public TreeView(String id, AfpTreeModel afpTreeModel) {
        super(id);

        this.afpTreeModel = afpTreeModel;

        add(new AjaxLink<Void>("expandAll") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                tree.getTreeState().expandAll();
                tree.update();
            }
        });

        add(new AjaxLink<Void>("collapseAll") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                tree.getTreeState().collapseAll();
                tree.update();
            }
        });

        rootNode = new DefaultMutableTreeNode(new SfModelBean("Root Node", "AFP File"));

        treeModel = new DefaultTreeModel(rootNode);

        List<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode>> columns = new ArrayList<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode>>();

        columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String>(Model
                .of("Offsets"), "userObject.getColumn1"));
        columns.add(new PropertyTreeColumn<DefaultTreeModel, DefaultMutableTreeNode, String>(Model
                .of("Structured Fields"), "userObject.getColumn2"));
        columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String>(Model
                .of("Values"), "userObject.getColumn3").setWrapText(true));
        tree = new TreeGrid<DefaultTreeModel, DefaultMutableTreeNode>("treegrid", treeModel,
                columns);
        tree.getTreeState().expandNode(treeModel.getRoot());
        add(tree);

    }

    public void update() {
        LOG.debug("update()");
        SfTreeNode nodes = afpTreeModel.getObject();
        if (nodes != null) {
            rootNode.removeAllChildren();
            tree.getTreeState().collapseAll();
            addToRoot(rootNode, nodes);
        }
    }

    private void addToRoot(DefaultMutableTreeNode parent, SfTreeNode nodes) {
        for (SfTreeNode node : nodes.getChilden()) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(new SfModelBean(
                    node.getField()));
            addParamsAndTriplets(child, node.getField());
            parent.add(child);
            addToRoot(child, node);
        }
    }

    private void addParamsAndTriplets(DefaultMutableTreeNode parent, StructuredField sf) {
        DefaultMutableTreeNode parameters = new DefaultMutableTreeNode(new SfModelBean("",
                "Parameters"));
        for (ParameterAsString param : sf.getParameters()) {
            parameters.add(new DefaultMutableTreeNode(new SfModelBean("", param.getKey(), param
                    .getValue())));
        }
        parent.add(parameters);
        if (sf instanceof StructuredFieldWithTriplets) {
            List<ParameterAsString> tripletParameters = ((StructuredFieldWithTriplets) sf)
                    .getParameters();
            DefaultMutableTreeNode triplets = new DefaultMutableTreeNode(new SfModelBean("",
                    "Triplets"));
            for (ParameterAsString param : tripletParameters) {
                triplets.add(new DefaultMutableTreeNode(new SfModelBean("", param.getKey(), param
                        .getValue())));
            }
            if (tripletParameters.size() > 0) {
                parent.add(triplets);
            }
        } else if (sf instanceof StructuredFieldWithTripletGroup) {
            List<List<ParameterAsString>> repeatingGroupList = ((StructuredFieldWithTripletGroup) sf)
                    .getRepeatingGroupParameters();
            DefaultMutableTreeNode repeatingGroup = new DefaultMutableTreeNode(new SfModelBean("",
                    "RepeatingGroup"));
            for (List<ParameterAsString> rGroup : repeatingGroupList) {
                DefaultMutableTreeNode rg = new DefaultMutableTreeNode(new SfModelBean("", "Group"));
                for (ParameterAsString tripletParam : rGroup) {
                    rg.add(new DefaultMutableTreeNode(new SfModelBean("", tripletParam.getKey(),
                            tripletParam.getValue())));
                }
                repeatingGroup.add(rg);
            }
            if (repeatingGroupList.size() > 0) {
                parent.add(repeatingGroup);
            }
        }
    }

    private static class SfModelBean implements Serializable {
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

}
