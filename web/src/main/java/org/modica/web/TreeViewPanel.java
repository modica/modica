package org.modica.web;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.modica.web.filepicker.FileModel;
import org.modica.web.filepicker.FilePicker;
import org.modica.web.model.AfpService;
import org.modica.web.model.AfpTreeModel;
import org.modica.web.treeview.TreeView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreeViewPanel extends Panel {

    private static final Logger LOG = LoggerFactory.getLogger(TreeViewPanel.class);

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
                LOG.debug("New afp file set");
                super.setObject(file);
                ModicaSession session = ModicaSession.get();
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
                treeView.update();
            }
        };
        FilePicker filePicker = new FilePicker("filePicker", fileModel);
        treeView = new TreeView("treeView", new AfpTreeModel());
        add(filePicker);
        add(treeView);
    }
}
