package org.modica.parser

import static org.mockito.Mockito.*

import org.modica.afp.modca.StructuredFieldFactory
import org.modica.afp.modca.structuredfields.CategoryCode
import org.modica.afp.modca.structuredfields.StructuredField
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer
import org.modica.afp.modca.structuredfields.TypeCode

import org.junit.Test

class StructuredFieldCreatorTestCase {

    @Test
    void handleBegin() {
        def createdSf = {} as StructuredField
        def sfFactory = [createBegin: {createdSf}] as StructuredFieldFactory

        def handledSf = null
        def sfHandler = {handledSf = it} as StructuredFieldHandler

        new StructuredFieldCreator(sfFactory, sfHandler).handleBegin(null)

        assert handledSf.is(createdSf)
    }

    @Test
    void handleEnd() {
        def createdSf = {} as StructuredField
        def sfFactory = [createEnd: {createdSf}] as StructuredFieldFactory

        def handledSf = null
        def sfHandler = {handledSf = it} as StructuredFieldHandler

        new StructuredFieldCreator(sfFactory, sfHandler).handleEnd(null)

        assert handledSf.is(createdSf)
    }

    @Test
    void handle() {
        CategoryCode.with { [(TypeCode.Map): medium,
                (TypeCode.Descriptor): medium,
                (TypeCode.Migration): page,
                (TypeCode.Data): font,
                (TypeCode.Position): page,
                (TypeCode.Include): page,
                (TypeCode.Control): medium,
                (TypeCode.Index): font
            ] }.each { code, catagory ->
            def createdSf = {} as StructuredField
            def sfFactory = [("create$code".toString()): {createdSf}] as StructuredFieldFactory
            def handledSf = null
            def sfHandler = {sf -> handledSf = sf} as StructuredFieldHandler
            def introducer = [0L, 0, [0xD3, code.value, catagory.value] as byte[], (byte) 0, 0] as StructuredFieldIntroducer
            new StructuredFieldCreator(sfFactory, sfHandler).handle(introducer)
            assert handledSf.is(createdSf)
        }
    }

    @Test
    void startAndEndAfp() {
        def sfHandler = mock(StructuredFieldHandler)
        def sut = new StructuredFieldCreator(null, sfHandler)
        ['startAfp', 'endAfp'].each { method ->
            sut."$method"()
            verify(sfHandler)."$method"()
        }
    }
}
