package org.afpparser.parser;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual
import static org.junit.Assert.*

import java.io.File

import org.afpparser.serializer.xml.XmlSFIntroducerSerializer;
import org.junit.Test
import org.xml.sax.InputSource

class AFPDocumentParserIntegrationTest {

    @Test
    public void test() {
//        new XmlSfSerializer(resourceToFile('org/afpparser/parser/test.afp'))
//                .writeTo( new File('/tmp/afp.xml'))

        // How can we stream the output for testing?
        final ByteArrayOutputStream baos = new ByteArrayOutputStream()

        new XmlSFIntroducerSerializer(resourceToStream('org/afpparser/parser/test.afp')).writeTo(baos)

        final ByteArrayInputStream actual = new ByteArrayInputStream(baos.toByteArray())

        final expected = resourceToStream('org/afpparser/parser/expected.xml')

        assertXMLEqual(new InputSource(expected),  new InputSource(actual))
    }

    private FileInputStream resourceToStream(String resource) {
        URI uri = this.class.classLoader.getResource(resource).toURI()
        FileInputStream inStream = new FileInputStream(uri.toURL().getFile())
        assert inStream
        return inStream
    }
}
