package com.afpparser.parser
import groovy.mock.interceptor.StubFor
import groovy.mock.interceptor.MockFor
import org.afpparser.afp.modca.structuredfields.StructuredField
import org.afpparser.parser.PrintingSFHandler
import org.junit.Test;

class PrintingSFHandlerTestCase {
    @Test
    void handle() {
        def baos = new ByteArrayOutputStream()
        def sut = new PrintingSFHandler(new PrintStream(baos, true))
        def sf = {} as StructuredField
        
        def expected = new ByteArrayOutputStream()
        new PrintStream(expected, true).println(sf)
        
        sut.handle(sf)
        
        assert baos.toByteArray() == expected.toByteArray()
    }
}
