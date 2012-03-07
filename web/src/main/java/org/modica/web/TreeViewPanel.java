package org.modica.web;

import java.io.File;

import org.apache.wicket.markup.html.panel.Panel;
import org.modica.web.filepicker.FileModel;
import org.modica.web.filepicker.FilePicker;
import org.modica.web.treeview.TreeView;

public class TreeViewPanel extends Panel {

    private final TreeView treeView;

    private static final long serialVersionUID = 1L;

    public TreeViewPanel(String id) {
        super(id);

        final FileModel fileModel = new FileModel() {
            @Override
            public void setObject(File file) {
                super.setObject(file);
                // TODO what is the idiomatic Wicket for syncronizing between components?
                // Which component method of TreeView can we override to refresh on FileModel?
                treeView.update();
            }

        };
        FilePicker filePicker = new FilePicker("filePicker", fileModel);
        treeView = new TreeView("treeView", fileModel);
        add(filePicker);
        add(treeView);
    }

}
