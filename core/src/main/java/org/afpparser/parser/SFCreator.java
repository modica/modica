package org.afpparser.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredField;
import org.afpparser.afp.modca.structuredfields.begin.BeginObjectHandler;
import org.afpparser.afp.modca.structuredfields.map.MapObjectHandler;

public class SFCreator implements SFIntroducerHandler {

    private final FileChannel channel;

    private final List<StructuredField> structuredFields;

    public SFCreator(FileChannel channel) {
        this.channel = channel;
        structuredFields = new ArrayList<StructuredField>();
    }

    public byte[] createStructuredField(SfIntroducer intro) {
        try {
            long byteOffset = intro.getOffset() + SfIntroducer.SF_Introducer_FIELD
                    + SfIntroducer.Carriage_Control_FIELD;
            if (intro.hasExtData()) {
                byteOffset += intro.getExtLength() + SfIntroducer.ExtLength_FIELD;
            }
            ByteBuffer buffer = ByteBuffer.allocate(
                                          intro.getLength()
                                                  - SfIntroducer.SF_Introducer_FIELD
                                                  - SfIntroducer.Carriage_Control_FIELD);
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
    public void handleBegin(SfIntroducer sf) {
        StructuredField struct = BeginObjectHandler.handle(sf, createStructuredField(sf));
        System.out.println(struct);
        structuredFields.add(struct);
    }

    @Override
    public void handleEnd(SfIntroducer sf) {
    }

    @Override
    public void handle(SfIntroducer sf) {
        StructuredField field;
        switch (sf.getType().getTypeCode()) {
        case Map:
            field = MapObjectHandler.handle(sf, createStructuredField(sf));
            break;
        default:
            field = null;
        }
        structuredFields.add(field);
        System.out.println(field);
    }
}
