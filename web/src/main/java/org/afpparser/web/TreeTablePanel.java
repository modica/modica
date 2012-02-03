package org.afpparser.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.column.tree.PropertyTreeColumn;
import com.inmethod.grid.treegrid.TreeGrid;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class TreeTablePanel extends BaseTreePanel {

    private static final long serialVersionUID = 1L;

    private final TreeGrid<DefaultTreeModel, DefaultMutableTreeNode> tree;

    @SpringBean
    private AfpTreeBuilder afpParser;

    public void updateTree(File afpDocument) throws IOException {
        emptyTree();
        addToTree(afpParser.buildTree(afpDocument));
    }

    public TreeTablePanel(String id) {
        super(id);
        final FileUploadForm simpleUploadForm = new FileUploadForm("simpleUpload", this);
        add(simpleUploadForm);

        List<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode>> columns = new ArrayList<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode>>();

        columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String>(Model
                .of("Offsets"), "userObject.getColumn1"));
        columns.add(new PropertyTreeColumn<DefaultTreeModel, DefaultMutableTreeNode, String>(Model
                .of("Structured Fields"), "userObject.getColumn2"));
        columns.add(new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String>(Model
                .of("Values"), "userObject.getColumn3").setWrapText(true));
        DefaultTreeModel model = createTreeModel();
        tree = new TreeGrid<DefaultTreeModel, DefaultMutableTreeNode>("treegrid", model, columns);
        tree.getTreeState().expandNode(model.getRoot());
        add(tree);
    }

    @Override
    TreeGrid<DefaultTreeModel, DefaultMutableTreeNode> getTree() {
        return tree;
    }
}
