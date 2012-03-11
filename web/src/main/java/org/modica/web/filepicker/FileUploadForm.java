package org.modica.web.filepicker;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;

public class FileUploadForm extends Form<Void> {

    private static final long serialVersionUID = 1L;

    private final FileUploadField fileUploadField;

    private final IModel<File> fileModel;

    public FileUploadForm(String name, final IModel<File> fileModel) {
        super(name);
        this.fileModel = fileModel;
        fileUploadField = new FileUploadField("fileInput");
        add(fileUploadField);
    }

    @Override
    protected void onSubmit() {
        final FileUpload upload = fileUploadField.getFileUpload();
        File newFile;
        try {
            newFile = File.createTempFile("modica_", ".afp");
            newFile.deleteOnExit();
        } catch (IOException e1) {
            throw new WicketRuntimeException(e1);
        }
        try {
            upload.writeTo(newFile);
            fileModel.setObject(newFile);
        } catch (Exception e) {
            throw new WicketRuntimeException(e);
        }
    }

}
