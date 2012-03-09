package org.modica.web;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.modica.web.filepicker.FileModel;
import org.modica.web.filepicker.FilePicker;
import org.modica.web.model.AfpService;
import org.modica.web.treeview.TreeView;

public class TreeViewPanel extends Panel {

    private final TreeView treeView;

    private static final long serialVersionUID = 1L;

    @SpringBean
    AfpService afpService;

    public TreeViewPanel(String id) {
        super(id);

        final FileModel fileModel = new FileModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public void setObject(File file) {
                super.setObject(file);
                ModicaSession session = (ModicaSession) getSession();
                File previous = session.getAfpFile();
                if (previous != null) {
                    previous.delete();
                }
                session.setAfpFile(file);
                try {
                    afpService.loadDocument(file);
                } catch (FileNotFoundException e) {
                    throw new WicketRuntimeException("Faulty afp file " + file, e);
                }
                // TODO what is the idiomatic Wicket for syncronizing between components?
                // Which component method of TreeView can we override to refresh on FileModel?
                treeView.update();
            }
        };
        FilePicker filePicker = new FilePicker("filePicker", fileModel);
        treeView = new TreeView("treeView");
        add(filePicker);
        add(treeView);
    }

}
