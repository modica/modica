package org.afpparser.afp.modca;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredField;
import org.afpparser.afp.modca.structuredfields.begin.BeginHandler;
import org.afpparser.afp.modca.structuredfields.control.ControlHandler;
import org.afpparser.afp.modca.structuredfields.data.DataHandler;
import org.afpparser.afp.modca.structuredfields.descriptor.DescriptorHandler;
import org.afpparser.afp.modca.structuredfields.end.EndHandler;
import org.afpparser.afp.modca.structuredfields.include.IncludeHandler;
import org.afpparser.afp.modca.structuredfields.index.IndexHandler;
import org.afpparser.afp.modca.structuredfields.map.MapObjectHandler;
import org.afpparser.afp.modca.structuredfields.migration.MigrationHandler;
import org.afpparser.afp.modca.structuredfields.position.PositionHandler;

/**
 * A plain vanilla Structured Field factory that creates objects of each type
 * and does nothing else.
 */
public class StructuredFieldFactoryImpl implements StructuredFieldFactory {

    private final FileChannel channel;
    private final Context context = new Context();

    public StructuredFieldFactoryImpl(FileChannel channel) {
        this.channel = channel;
    }

    public Parameters createStructuredField(SfIntroducer intro) {
        try {
            long byteOffset = intro.getDataOffset();
            ByteBuffer buffer = ByteBuffer.allocate(intro.getLength()
                    - SfIntroducer.SF_Introducer_FIELD - SfIntroducer.Carriage_Control_FIELD);
            channel.read(buffer, byteOffset);
            return new Parameters(buffer.array());
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    @Override
    public StructuredField createBegin(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return BeginHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createMap(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return MapObjectHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createDescriptor(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return DescriptorHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createMigration(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return MigrationHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createEnd(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return EndHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createData(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return DataHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createPosition(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return PositionHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createInclude(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return IncludeHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createControl(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return ControlHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createIndex(SfIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return IndexHandler.handle(introducer, payload, context);
    }
}
