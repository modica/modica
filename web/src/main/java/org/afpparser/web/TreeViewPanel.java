package org.afpparser.web;

import org.afpparser.web.filepicker.FileModel;
import org.afpparser.web.filepicker.FilePicker;
import org.afpparser.web.treeview.TreeView;
import org.apache.wicket.markup.html.panel.Panel;

public class TreeViewPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public TreeViewPanel(String id) {
        super(id);

        final FileModel fileModel = new FileModel();

        TreeView treeView = new TreeView("treeView", fileModel);
        treeView.setOutputMarkupId(true);
        FilePicker filePicker = new FilePicker("filePicker", fileModel, treeView);

        add(filePicker);

        add(treeView);
    }
}
