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

package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.triplets.TripletIdentifiers;
import org.modica.afp.modca.triplets.TripletTestCase;

import static org.junit.Assert.assertEquals;

public class FQNCharStringDataTestCase extends TripletTestCase<FQNCharStringData> {
    private FQNCharStringData x;
    private FQNType type;
    private String expectedString;
    private int length;

    @Override
    @Before
    public void setUp() {
        expectedString = "Test String";
        length = 1;
        type = FQNType.font_charset_name_ref;
        this.x = new FQNCharStringData(length, expectedString, type);
        FQNCharStringData y = new FQNCharStringData(length, expectedString, type);
        FQNCharStringData z = new FQNCharStringData(length, expectedString, type);
        FQNCharStringData notEqual = new FQNCharStringData(length, "Not same string", type);
        setXYZ(x, y, z, notEqual);
    }

    @Test
    public void testGetters() {
        assertEquals(expectedString, x.getString());
        assertEquals(type, x.getFQNType());
        assertEquals(FQNFmt.character_string, x.getFormat());
        assertEquals(length, x.getLength());
        assertEquals(TripletIdentifiers.fully_qualified_name, x.getTid());
    }

    @Test
    @Override
    public void testGetParameters() {
        List<ParameterAsString> expectedParams = new ArrayList<ParameterAsString>();
        expectedParams.add(new ParameterAsString(type.name(), expectedString));
        parameterTester(expectedParams, x);
    }
}
