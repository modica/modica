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
            StructuredFieldIntroducerHandlers.chain([])
            fail()
        } catch(e) { }
    }

    @Test
    void chain() {
        def api = StructuredFieldIntroducerHandler.methods.inject([:]) {api, method ->
            api[method.name] = [null] * method.genericParameterTypes.length
            return api
        }

        (1..3).each { n ->
            def handlers = (1..n).collect{ mock(StructuredFieldIntroducerHandler) }
            def sut = StructuredFieldIntroducerHandlers.chain(handlers)
            api.each {method, parameters ->
                sut."$method"(*parameters)
            }
            handlers.each {
                api.keys.each {method ->
                    verify(it)."$method"(anyObject())
                }
            }
        }
    }

    @Test
    void chainNested() {
        def handlers = (1..2).collect{ mock(StructuredFieldIntroducerHandler) }
        def sut = StructuredFieldIntroducerHandlers.chain(mock(StructuredFieldIntroducerHandler),
                StructuredFieldIntroducerHandlers.chain(handlers))
        sut.startAfp()
        handlers.each {
            verify(it).startAfp()
        }
    }
}
