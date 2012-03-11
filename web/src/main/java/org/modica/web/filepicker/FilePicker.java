package org.modica.web.filepicker;

import java.io.File;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class FilePicker extends Panel {

    private static final long serialVersionUID = 1L;

    public FilePicker(String id, IModel<File> fileModel) {
        super(id);
        FileUploadForm fileUpoadForm = new FileUploadForm("fileUploadForm", fileModel);
        add(fileUpoadForm);
    }
}
