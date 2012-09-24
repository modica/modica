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

package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldTestCase;
import org.modica.afp.modca.structuredfields.types.EndType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link EndActiveEnvironmentGroup}.
 */
public class EndActiveEnvironmentGroupTestCase extends
        StructuredFieldTestCase<EndActiveEnvironmentGroup> {
    private EndActiveEnvironmentGroup sut;
    private EndActiveEnvironmentGroup sutMatchesAny;
    private final String aegName = "TestStri";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        StructuredFieldIntroducer intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(EndType.EAG);
        Parameters params = new Parameters(aegName.getBytes("Cp500"));
        sut = new EndActiveEnvironmentGroup(intro, params);

        Parameters matchesAnyParams = new Parameters(ByteUtils.createByteArray(0xff, 0xff));
        sutMatchesAny = new EndActiveEnvironmentGroup(intro, matchesAnyParams);

        setMembers(sut, intro);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(aegName, sut.getAegName());
        assertEquals(false, sut.nameMatchesAny());

        assertEquals(null, sutMatchesAny.getAegName());
        assertEquals(true, sutMatchesAny.nameMatchesAny());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString("ActiveEnvironmentGroupName", aegName));
        testParameters(expectedParams, sut);
    }
}
