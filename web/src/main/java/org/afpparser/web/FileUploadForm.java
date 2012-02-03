package org.afpparser.web;

import java.io.File;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class FileUploadForm extends Form<Void> {
    private static final long serialVersionUID = 1L;
    private FileUploadField fileUploadField;
    private transient final TreeTablePanel page;

    public FileUploadForm(String name, TreeTablePanel page) {
        super(name);
        add(fileUploadField = new FileUploadField("fileInput"));
        this.page = page;
    }

    @Override
    protected void onSubmit() {
        final FileUpload upload = fileUploadField.getFileUpload();
        File newFile = new File(getUploadFolder(), upload.getClientFileName());
        try {
            upload.writeTo(newFile);
            page.updateTree(newFile);
            newFile.delete();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String getUploadFolder() {
        return System.getProperty("java.io.tmpdir");
    }
}
