package org.modica.afp.modca;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.begin.BeginHandler;
import org.modica.afp.modca.structuredfields.control.ControlHandler;
import org.modica.afp.modca.structuredfields.data.DataHandler;
import org.modica.afp.modca.structuredfields.descriptor.DescriptorHandler;
import org.modica.afp.modca.structuredfields.end.EndHandler;
import org.modica.afp.modca.structuredfields.include.IncludeHandler;
import org.modica.afp.modca.structuredfields.index.IndexHandler;
import org.modica.afp.modca.structuredfields.map.MapObjectHandler;
import org.modica.afp.modca.structuredfields.migration.MigrationHandler;
import org.modica.afp.modca.structuredfields.position.PositionHandler;

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

    private Parameters createStructuredField(StructuredFieldIntroducer intro) {
        try {
            long byteOffset = intro.getDataOffset();
            ByteBuffer buffer = ByteBuffer.allocate(intro.getLength()
                    - StructuredFieldIntroducer.SF_Introducer_FIELD - StructuredFieldIntroducer.Carriage_Control_FIELD);
            channel.read(buffer, byteOffset);
            return new Parameters(buffer.array(), (Integer) context.get(ContextType.MODCA_GCSGID));
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    @Override
    public StructuredField createBegin(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return BeginHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createMap(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return MapObjectHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createDescriptor(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return DescriptorHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createMigration(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return MigrationHandler.handle(introducer, payload);
    }

    @Override
    public StructuredField createEnd(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return EndHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createData(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return DataHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createPosition(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return PositionHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createInclude(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return IncludeHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createControl(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return ControlHandler.handle(introducer, payload, context);
    }

    @Override
    public StructuredField createIndex(StructuredFieldIntroducer introducer) {
        Parameters payload = createStructuredField(introducer);
        return IndexHandler.handle(introducer, payload, context);
    }
}
