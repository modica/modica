package org.afpparser.serializer.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.afpparser.parser.AFPDocumentParser;
import org.afpparser.serializer.StructuredFieldSerializer;

/**
 * Used to parse an AFP file and serialize to xml form.
 *
 */
public class XmlSfSerializer implements StructuredFieldSerializer {
	
    private final FileInputStream afpInputStream;

	/**
	 * @param afpFile afp file to serialize
	 */
    public XmlSfSerializer(FileInputStream afpInputStream) {
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
		XmlSerializingSfHandler handler = new XmlSerializingSfHandler(out);
        new AFPDocumentParser(afpInputStream, handler).parse();
	}
}
