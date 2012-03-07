package org.modica.parser

import groovy.mock.interceptor.MockFor
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer
import org.modica.afp.modca.structuredfields.TypeCode
import org.modica.afp.modca.structuredfields.CategoryCode

import org.junit.Test

class StructuredFieldIntroducerParserTestCase {
    @Test
    void parse() {
        [   handle:         [TypeCode.Descriptor.value, CategoryCode.medium.value],
            handleBegin:    [TypeCode.Begin.value, CategoryCode.page.value],
            handleEnd:      [TypeCode.End.value, CategoryCode.page.value]
        ].each { handleMethod, code ->
            def sf =  new StructuredFieldIntroducer(0L, 0, [0xD3, *code] as byte[], (byte) 0, 0)
            def handledSfIntroducer = null
            def handler = [(handleMethod): {handledSfIntroducer = it}, startAfp: {},
                        endAfp: {}] as StructuredFieldIntroducerHandler
            def reader = [iterator: {[sf].iterator()}] as Iterable
            def sut = new StructuredFieldIntroducerParser(reader, handler)
            sut.parse()
            assert handledSfIntroducer.is(sf)
        }
    }
}
