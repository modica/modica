package org.modica.web.filepicker;

import java.io.File;

import org.apache.wicket.model.IModel;

public class FileModel implements IModel<File> {

    private static final long serialVersionUID = 1L;

    private transient File file;

    @Override
    public void detach() {
        file = null;
    }

    @Override
    public File getObject() {
        return file;
    }

    @Override
    public void setObject(File file) {
        this.file = file;
    }
}
