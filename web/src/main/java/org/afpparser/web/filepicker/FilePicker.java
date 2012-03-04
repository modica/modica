package org.afpparser.web.filepicker;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;

public class FilePicker extends Panel {

    private static final long serialVersionUID = 1L;

    public FilePicker(String id, FileModel fileModel, Component... registeredComponents) {
        super(id);
        FileUploadForm fileUpoadForm = new FileUploadForm("fileUploadForm", fileModel,
                registeredComponents);
        add(fileUpoadForm);
    }
}
