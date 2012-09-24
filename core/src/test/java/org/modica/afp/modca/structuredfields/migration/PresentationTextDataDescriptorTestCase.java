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

package org.modica.afp.modca.structuredfields.migration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.structuredfields.types.MigrationType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * A test case for {@link PresentationTextDataDescriptor}.
 */
public class PresentationTextDataDescriptorTestCase extends
        StructuredFieldWithTripletsTestCase<PresentationTextDataDescriptor> {

    private PresentationTextDataDescriptor sut;

    @Before
    public void setUp() {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(MigrationType.PTD);

        byte[] sfData = ByteUtils.createByteArray(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0);
        Parameters params = new Parameters(sfData);
        sut = new PresentationTextDataDescriptor(intro, params);
        setMembers(sut, intro, Collections.<Triplet> emptyList());
    }

    @Test
    public void testGetterMethods() {
        assertEquals(0x102, sut.getXptUnits());
        assertEquals(0x304, sut.getYptUnits());
        assertEquals(0x50607, sut.getXptSize());
        assertEquals(0x8090A, sut.getYptSize());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("X-AxisUnits", 0x102));
        expectedParams.add(new ParameterAsString("Y-AxisUnits", 0x304));
        expectedParams.add(new ParameterAsString("X-AxisSize", 0x50607));
        expectedParams.add(new ParameterAsString("Y-AxisSize", 0x8090A));
        testParameters(expectedParams, sut);
    }
}
