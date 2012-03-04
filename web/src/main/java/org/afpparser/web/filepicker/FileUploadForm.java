package org.afpparser.web.filepicker;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class FileUploadForm extends Form<Void> {

    private static final long serialVersionUID = 1L;

    private final static File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    private final FileUploadField fileUploadField;

    private final List<Component> registeredComponents;

    public FileUploadForm(String name, final FileModel fileModel, Component... componentToUpdates) {
        super(name);
        registeredComponents = Arrays.asList(componentToUpdates);
        fileUploadField = new FileUploadField("fileInput");
        fileUploadField.add(new AjaxFormSubmitBehavior(this, "onchange") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                final FileUpload upload = fileUploadField.getFileUpload();
                File newFile = new File(TMP_DIR, upload.getClientFileName());
                try {
                    upload.writeTo(newFile);
                    fileModel.setObject(newFile);
                    newFile.delete();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
                updateComponents(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                throw new WicketRuntimeException("File input exception");
            }
        });

        add(fileUploadField);
    }

    public void updateComponents(AjaxRequestTarget target) {
        for (Component component : registeredComponents) {
            target.add(component);
        }
    }
}
