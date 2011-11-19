package org.afpparser.serializer.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.afpparser.parser.AFPDocumentParser;
import org.afpparser.serializer.SFIntroducerSerializer;

/**
 * Used to parse an AFP file and serialise to XML form.
 */
public class XmlSFIntroducerSerializer implements SFIntroducerSerializer {

    private final FileInputStream afpInputStream;

    /**
     * @param afpFile the AFP file to serialise
     */
    public XmlSFIntroducerSerializer(FileInputStream afpInputStream) {
        this.afpInputStream = afpInputStream;
    }

    @Override
    public void writeTo(File outFile) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
        try {
            writeTo(out);
        } finally {
            out.close();
        }
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        XmlSerializingSFIntroducerHandler handler = new XmlSerializingSFIntroducerHandler(out);
        new AFPDocumentParser(afpInputStream, handler).parse();
    }
}
