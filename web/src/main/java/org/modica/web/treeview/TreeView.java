package org.modica.web.treeview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.web.filepicker.FileModel;
import org.modica.web.model.AfpService;
import org.modica.web.model.AfpTreeBuilder;
import org.modica.web.model.SfModelBean;
import org.modica.web.model.SfTreeNode;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.tree.PropertyTreeColumn;
import com.inmethod.grid.treegrid.TreeGrid;

public class TreeView extends Panel {

    private final DefaultMutableTreeNode rootNode;

    private final DefaultTreeModel treeModel;


    private final TreeGrid<DefaultTreeModel, DefaultMutableTreeNode> tree;

    @SpringBean
    AfpService afpService;

    public TreeView(String id) {
        super(id);

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
        rootNode.removeAllChildren();
        tree.getTreeState().collapseAll();
        addToRoot(rootNode, afpService.buildTree());
    }

    private void addToRoot(DefaultMutableTreeNode parent, SfTreeNode nodes) {
        for (Object node : nodes.getChilden()) {
            if (node instanceof SfTreeNode) {
                SfTreeNode tn = (SfTreeNode) node;
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new SfModelBean(
                        tn.getField()));
                addParamsAndTriplets(child, tn.getField());
                parent.add(child);
                addToRoot(child, tn);
            } else {
                StructuredField sf = (StructuredField) node;
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(new SfModelBean(sf));
                addParamsAndTriplets(child, sf);
                parent.add(child);
            }
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

}
