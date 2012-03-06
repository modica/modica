package org.afpparser.web;

import java.io.File;

import org.afpparser.web.filepicker.FileModel;
import org.afpparser.web.filepicker.FilePicker;
import org.afpparser.web.treeview.TreeView;
import org.apache.wicket.markup.html.panel.Panel;

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
