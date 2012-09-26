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

package org.modica.afp.modca.structuredfields.data;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.ptoca.AbsoluteMoveBaseline;
import org.modica.afp.ptoca.ControlSequence;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link PresentationTextData}.
 */
public class PresentationTextDataTestCase extends StructuredFieldTestCase<PresentationTextData> {

    private PresentationTextData sut;
    private final String controlSequence = "2BD304D30370";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(BeginType.BPT);

        Parameters params = new Parameters(ByteUtils.hexToBytes(controlSequence));
        sut = new PresentationTextData(intro, params, new ContextImpl());
        setMembers(sut, intro);
    }

    @Test
    public void testGetText() throws UnsupportedEncodingException {
        List<ControlSequence> cs = sut.getPtocaData();
        assertEquals(1, cs.size());
        AbsoluteMoveBaseline amb = (AbsoluteMoveBaseline) cs.get(0);
        assertEquals(880, amb.getDisplacement());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> params = sut.getParameters();
        assertEquals(0, params.size());
    }
}
