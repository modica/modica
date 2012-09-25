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

package org.modica.afp.modca.structuredfields.types;

import org.junit.Test;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldType.NotYetImplementedBuilder;
import org.modica.afp.modca.structuredfields.attribute.TagLogicalElement.TLEBuilder;
import org.modica.parser.StructuredFieldIntroducerHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.modica.afp.modca.structuredfields.types.AttributeType.MFC;
import static org.modica.afp.modca.structuredfields.types.AttributeType.TLE;

public class AttributeTypeTestCase {

    @Test
    public void testGetName() {
        assertEquals("Medium Finishing Control", MFC.getName());
        assertEquals("Tag Logical Element", TLE.getName());
    }

    @Test
    public void testGetBuilder() {
        assertTrue(MFC.getBuilder() instanceof NotYetImplementedBuilder);
        assertTrue(TLE.getBuilder() instanceof TLEBuilder);
    }

    @Test
    public void testHandleIntroducer() {
        StructuredFieldIntroducerHandler handler = mock(StructuredFieldIntroducerHandler.class);
        StructuredFieldIntroducer intro = mock(StructuredFieldIntroducer.class);
        AttributeType.TLE.handleIntroducer(handler, intro);
        verify(handler).handle(intro);
    }
}
