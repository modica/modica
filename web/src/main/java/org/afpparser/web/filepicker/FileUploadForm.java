package org.afpparser.web.filepicker;

import java.io.File;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class FileUploadForm extends Form<Void> {

    private static final long serialVersionUID = 1L;

    private final static File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    private FileUploadField fileUploadField;

    private final FileModel fileModel;

    public FileUploadForm(String name, FileModel fileModel) {
        super(name);
        this.fileModel = fileModel;
        add(fileUploadField = new FileUploadField("fileInput"));
    }

    @Override
    protected void onSubmit() {
        final FileUpload upload = fileUploadField.getFileUpload();
        File newFile = new File(TMP_DIR, upload.getClientFileName());
        try {
            upload.writeTo(newFile);
            fileModel.setObject(newFile);
            newFile.delete();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
