package com.afpparser.parser;

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.afpparser.parser.StructuredFieldHandler
import org.afpparser.parser.StructuredFieldHandlers
import org.junit.Test

class StructuredFieldHandlersTestCase {

    @Test
    void chainNoArgs() {
        try {
            StructuredFieldHandlers.chain([])
            fail()
        } catch(IllegalArgumentException e) { }
    }

    @Test
    void chain() {
        (1..3).each {n ->
            def handlers = (1..n).collect{ mock(StructuredFieldHandler) }
            def handler = StructuredFieldHandlers.chain(handlers)
            handler.handle(null)
            handlers.each {
                verify(it).handle(anyObject())
            }
        }
    }

    @Test
    void chainNested() {
        def handlers = (1..2).collect{ mock(StructuredFieldHandler) }
        def sut = StructuredFieldHandlers.chain(mock(StructuredFieldHandler),
            StructuredFieldHandlers.chain(handlers))
        sut.handle(null)
        handlers.each {
            verify(it).handle(anyObject())
        }
    }
}
