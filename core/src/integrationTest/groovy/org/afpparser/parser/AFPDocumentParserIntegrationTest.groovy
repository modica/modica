package org.afpparser.parser;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual
import static org.junit.Assert.*

import java.io.File
import java.io.IOException
import java.io.OutputStream

import org.afpparser.serializer.xml.XmlSerializingSFIntroducerHandler
import org.junit.Test
import org.xml.sax.InputSource

class AFPDocumentParserIntegrationTest {

    @Test
    public void test() {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream()

        toXML(resourceToStream('org/afpparser/parser/test.afp'), baos)

        final ByteArrayInputStream actual = new ByteArrayInputStream(baos.toByteArray())

        assertXMLEqual(new InputSource(resourceToStream('org/afpparser/parser/expected.xml')),
                new InputSource(actual))
    }

    private static FileInputStream resourceToStream(String resource) {
        URI uri = this.classLoader.getResource(resource).toURI()
        FileInputStream inStream = new FileInputStream(uri.toURL().getFile())
        assert inStream
        return inStream
    }

    private static void toXML(InputStream afpInputStream, OutputStream outputStream) throws IOException {
        XmlSerializingSFIntroducerHandler handler = new XmlSerializingSFIntroducerHandler(outputStream)
        new StructuredFieldIntroducerParser(afpInputStream, handler).parse()
    }

    public static main(args) {
        new File(args[0]).withOutputStream { output ->
            toXML(resourceToStream('org/afpparser/parser/test.afp'), output)
        }
    }
}
