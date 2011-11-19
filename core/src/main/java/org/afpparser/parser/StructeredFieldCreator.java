package org.afpparser.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredField;
import org.afpparser.afp.modca.StructuredFieldFactory;
import org.afpparser.afp.modca.StructuredFieldFactoryImpl;

public class StructeredFieldCreator implements SFIntroducerHandler {

    private final FileChannel channel;

    private final StrucuredFieldCreationHandler creationHandler = new StrucuredFieldCreationHandler();
    private final StructuredFieldFactory sfFactory = new StructuredFieldFactoryImpl();

    public StructeredFieldCreator(FileChannel channel) {
        this.channel = channel;
    }

    public byte[] createStructuredField(SfIntroducer intro) {
        try {
            long byteOffset = intro.getOffset() + SfIntroducer.SF_Introducer_FIELD
                    + SfIntroducer.Carriage_Control_FIELD;
            if (intro.hasExtData()) {
                byteOffset += intro.getExtLength() + SfIntroducer.ExtLength_FIELD;
            }
            ByteBuffer buffer = ByteBuffer.allocate(intro.getLength()
                    - SfIntroducer.SF_Introducer_FIELD - SfIntroducer.Carriage_Control_FIELD);
            channel.read(buffer, byteOffset);
            return buffer.array();
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    @Override
    public void startAfp() {
    }

    @Override
    public void endAfp() {
    }

    @Override
    public void handleBegin(SfIntroducer introducer) {
        StructuredField structuredField = sfFactory.createBegin(introducer,
                createStructuredField(introducer));
        handle(structuredField);
    }

    @Override
    public void handleEnd(SfIntroducer sf) {
    }

    @Override
    public void handle(SfIntroducer introducer) {
        StructuredField structuredField;
        switch (introducer.getType().getTypeCode()) {
        case Map:
            structuredField = sfFactory.createMap(introducer, createStructuredField(introducer));
            break;
        default:
            structuredField = null;
        }
        handle(structuredField);
    }

    private void handle(StructuredField structuredField) {
        creationHandler.handle(structuredField);
    }
}
