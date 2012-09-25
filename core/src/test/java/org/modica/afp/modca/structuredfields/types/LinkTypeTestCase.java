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
import org.modica.parser.StructuredFieldIntroducerHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LinkTypeTestCase {

    @Test
    public void testGetName() {
        assertEquals("Link Logical Element", LinkType.LLE.getName());
    }

    @Test
    public void testGetBuilder() {
        assertTrue(LinkType.LLE.getBuilder() instanceof NotYetImplementedBuilder);
    }

    @Test
    public void testHandleIntroducer() {
        StructuredFieldIntroducerHandler handler = mock(StructuredFieldIntroducerHandler.class);
        StructuredFieldIntroducer intro = mock(StructuredFieldIntroducer.class);
        LinkType.LLE.handleIntroducer(handler, intro);
        verify(handler).handle(intro);
    }
}
