/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.afp.modca;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.structuredfields.StructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * A plain vanilla Structured Field factory that creates objects of each type
 * and does nothing else.
 */
public class StructuredFieldFactoryImpl implements StructuredFieldFactory {

    private final FileChannel channel;

    private final Context context;


    public StructuredFieldFactoryImpl(FileChannel channel) {
        this.channel = channel;
        this.context = new ContextImpl();
    }

    public StructuredFieldFactoryImpl(FileChannel channel, Context context) {
        this.channel = channel;
        this.context = context;
    }

    private StructuredField createStructuredField(StructuredFieldIntroducer intro) {
        try {
            long byteOffset = intro.getDataOffset();
            ByteBuffer buffer = ByteBuffer.allocate(intro.getLength()
                    - StructuredFieldIntroducer.SF_Introducer_FIELD - StructuredFieldIntroducer.Carriage_Control_FIELD);
            channel.read(buffer, byteOffset);
            Parameters params =  new Parameters(buffer.array(), (Integer) context.get(ContextType.MODCA_GCSGID));
            return intro.getType().getBuilder().build(intro, params, context);
        } catch (MalformedURLException mue) {
            throw new IllegalStateException(mue);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(uee);
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    @Override
    public StructuredField createBegin(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createMap(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createDescriptor(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createMigration(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createEnd(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createData(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createPosition(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createInclude(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createControl(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createIndex(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createAttribute(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createCopyCount(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createProcess(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createOrientation(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createTable(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createVariable(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }

    @Override
    public StructuredField createLink(StructuredFieldIntroducer introducer) {
        return createStructuredField(introducer);
    }
}
