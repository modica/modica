package org.afpparser.afp.modca;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.begin.BeginObjectHandler;
import org.afpparser.afp.modca.structuredfields.data.DataObjectHandler;
import org.afpparser.afp.modca.structuredfields.descriptor.DescriptorObjectHandler;
import org.afpparser.afp.modca.structuredfields.end.EndObjectHandler;
import org.afpparser.afp.modca.structuredfields.map.MapObjectHandler;
import org.afpparser.afp.modca.structuredfields.migration.MigrationObjectHandler;
import org.afpparser.afp.modca.structuredfields.position.PositionHandler;

/**
 * A plain vanilla Structured Field factory that creates objects of each type
 * and does nothing else.
 */
public class StructuredFieldFactoryImpl implements StructuredFieldFactory {

    private final FileChannel channel;

    public StructuredFieldFactoryImpl(FileChannel channel) {
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
    public StructuredField createBegin(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return BeginObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createMap(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return MapObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createDescriptor(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return DescriptorObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createMigration(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return MigrationObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createEnd(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return EndObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createData(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return DataObjectHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createPosition(SfIntroducer introducer) {
        byte[] payload = createStructuredField(introducer);
        return PositionHandler.handle(introducer, payload);
    }
}
