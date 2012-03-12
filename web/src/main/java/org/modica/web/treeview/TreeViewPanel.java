package org.modica.web.treeview;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.modica.web.filepicker.FilePicker;
import org.modica.web.model.AfpService;
import org.modica.web.model.AfpTreeModel;
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
        final Model<File> fileModel = new Model<File>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void setObject(File file) {
                LOG.debug("setObject()");
                super.setObject(file);
                try {
                    afpService.load(file);
                } catch (IOException e) {
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
