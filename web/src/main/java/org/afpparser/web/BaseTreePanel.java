package org.afpparser.web;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.afpparser.afp.modca.ParameterAsString;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.web.sftree.SfTreeNode;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.inmethod.grid.treegrid.TreeGrid;

public abstract class BaseTreePanel extends Panel {

    private static final long serialVersionUID = 1L;
    private final DefaultMutableTreeNode rootNode;
    private final DefaultTreeModel treeModel;

    public BaseTreePanel(String id) {
        super(id);
        add(new AjaxLink<Void>("expandAll") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                getTree().getTreeState().expandAll();
                getTree().update();
            }
        });

        add(new AjaxLink<Void>("collapseAll") {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(AjaxRequestTarget target) {
                getTree().getTreeState().collapseAll();
                getTree().update();
            }
        });
        rootNode = new DefaultMutableTreeNode(new SfModelBean("Root Node", "AFP File"));
        treeModel = new DefaultTreeModel(rootNode);
    }

    abstract TreeGrid<DefaultTreeModel, DefaultMutableTreeNode> getTree();

    protected DefaultTreeModel createTreeModel() {
        return treeModel;
    }

    void addToTree(SfTreeNode introducers) {
        add(rootNode, introducers);
    }

    public void emptyTree() {
        rootNode.removeAllChildren();
        getTree().getTreeState().collapseAll();
    }

    private void add(DefaultMutableTreeNode parent, SfTreeNode nodes) {
        for (Object node : nodes.getChilden()) {
            if (node instanceof SfTreeNode) {
                SfTreeNode tn = (SfTreeNode) node;
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new SfModelBean(tn.getField()));
                addParamsAndTriplets(child, tn.getField());
                parent.add(child);
                add(child, tn);
            } else {
                StructuredField sf = (StructuredField) node;
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new SfModelBean(sf));
                addParamsAndTriplets(child, sf);
                parent.add(child);
            }
        }
    }

    private void addParamsAndTriplets(DefaultMutableTreeNode parent, StructuredField sf) {
        DefaultMutableTreeNode parameters = new DefaultMutableTreeNode(new SfModelBean("", "Parameters"));
        for (ParameterAsString param : sf.getParameters()) {
            parameters.add(new DefaultMutableTreeNode(
                    new SfModelBean("", param.getKey(), param.getValue())));
        }
        parent.add(parameters);
        if (sf instanceof StructuredFieldWithTriplets) {
            List<ParameterAsString> tripletParameters = ((StructuredFieldWithTriplets) sf).getParameters();
            DefaultMutableTreeNode triplets = new DefaultMutableTreeNode(new SfModelBean("", "Triplets"));
            for (ParameterAsString param : tripletParameters) {
                triplets.add(new DefaultMutableTreeNode(
                        new SfModelBean("", param.getKey(), param.getValue())));
            }
            if (tripletParameters.size() > 0) {
                parent.add(triplets);
            }
        } else if (sf instanceof StructuredFieldWithTripletGroup) {
            List<List<ParameterAsString>> repeatingGroupList = ((StructuredFieldWithTripletGroup) sf).getRepeatingGroupParameters();
            DefaultMutableTreeNode repeatingGroup = new DefaultMutableTreeNode(new SfModelBean("", "RepeatingGroup"));
            for (List<ParameterAsString> rGroup : repeatingGroupList) {
                DefaultMutableTreeNode rg = new DefaultMutableTreeNode(new SfModelBean("", "Group"));
                for (ParameterAsString tripletParam : rGroup) {
                    rg.add(new DefaultMutableTreeNode(new SfModelBean("", tripletParam.getKey(), tripletParam.getValue())));
                }
                repeatingGroup.add(rg);
            }
            if (repeatingGroupList.size() > 0) {
                parent.add(repeatingGroup);
            }
        }
    }
}
