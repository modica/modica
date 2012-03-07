package org.modica.parser

import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer
import org.modica.common.ByteUtils
import org.modica.common.StringUtils
import org.modica.afp.modca.structuredfields.TypeCode

import org.junit.Test

class PrintingSFIntroducerHandlerTestCase {
    @Test
    void handle() {
        def baos = new ByteArrayOutputStream()
        def sut = new PrintingSFIntroducerHandler(new PrintStream(baos, true))
        def sf = [0L, 0, [0xD3, 0xA0, 0x88] as byte[], (byte) 0, 0] as StructuredFieldIntroducer

        def expected = new ByteArrayOutputStream()
        printSF (new PrintStream(expected, true), sf)

        play(sut) {handler ->
            handler.handle(sf)
        }
        assert Arrays.equals(baos.toByteArray(), expected.toByteArray())
    }

    @Test
    void handleBeginAndEnd() {
        def baos = new ByteArrayOutputStream()
        def sut = new PrintingSFIntroducerHandler(new PrintStream(baos, true))
        def beginSF = [0L, 0, [0xD3, TypeCode.Begin.value, 0xAF] as byte[], (byte) 0, 0] as StructuredFieldIntroducer
        def endSF = [0L, 0, [0xD3, TypeCode.End.value, 0xAF] as byte[], (byte) 0, 0] as StructuredFieldIntroducer

        def expected = new ByteArrayOutputStream()
        def ps = new PrintStream(expected, true)
        printSF(ps, beginSF)
        printSF(ps, endSF)

        play(sut) {handler ->
            handler.handleBegin(beginSF)
            handler.handleEnd(endSF)
        }

        assert Arrays.equals(baos.toByteArray(), expected.toByteArray())
    }

    private void play(handler, script) {
        handler.startAfp()
        script(handler)
        handler.endAfp()
    }

    private printSF(out, sf) {
        out.printf("\u001B[34m%s\u001B[0m  %s%s\n", StringUtils.toHex(sf.getOffset(), 8), "", sf.getType().getName())
    }
}
