package org.modica.web.lazy;

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

public class LazyViewPanel extends Panel {

    private static final Logger LOG = LoggerFactory.getLogger(LazyViewPanel.class);

    private final LazyView lazyView;

    private static final long serialVersionUID = 1L;

    @SpringBean
    AfpService afpService;

    public LazyViewPanel(String id) {
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
            }
        };
        FilePicker filePicker = new FilePicker("filePicker", fileModel);
        lazyView = new LazyView("lazyView", new AfpTreeModel());
        add(filePicker);
        add(lazyView);
    }

}
