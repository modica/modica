package com.afpparser.parser;

import static org.junit.Assert.*
import static org.mockito.Mockito.*

import org.afpparser.parser.StructuredFieldIntroducerHandler
import org.afpparser.parser.StructuredFieldIntroducerHandlers
import org.junit.Test

class StructuredFieldIntroducerHandlersTestCase {

    @Test
    void chainNoArgs() {
        try {
            StructuredFieldIntroducerHandlers.chain([]);
            fail();
        } catch(e) { }
    }

    @Test
    void chain() {
        def api = [startAfp: [], endAfp: [], handle: [null], handleBegin: [null], handleEnd: [null]]
        (1..3).each { n ->
            def handlers = (1..n).collect{ mock(StructuredFieldIntroducerHandler) }
            def handler = StructuredFieldIntroducerHandlers.chain(handlers)
            api.each {meth, args ->
                handler."$meth"(*args)
            }
            handlers.each {
                api.keys.each {meth ->
                verify(it)."$meth"(anyObject())
                }
            }
        }
    }

    @Test
    void chainNested() {
        def handlers = (1..2).collect{ mock(StructuredFieldIntroducerHandler) }
        def sut = StructuredFieldIntroducerHandlers.chain(mock(StructuredFieldIntroducerHandler),
            StructuredFieldIntroducerHandlers.chain(handlers))
        sut.handle(null)
        handlers.each {
            verify(it).handle(anyObject())
        }
    }
}
