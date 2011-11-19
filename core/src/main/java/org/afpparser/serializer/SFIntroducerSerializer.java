package org.afpparser.serializer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Used to write the state of a parsed AFP to a serial form.
 */
public interface SFIntroducerSerializer {
    /**
     * Write a serialised representation of an AFP to a file.
     *
     * @param file Output file to write to
     * @throws IOException io exception
     */
    void writeTo(File file) throws IOException;

    /**
     * Write a serialised representation of an AFP to an {@link OutputStream}.
     *
     * @param outputStream OutputStream to write to
     * @throws IOException io exception
     */
    void writeTo(OutputStream outputStream) throws IOException;
}
