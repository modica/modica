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

package org.modica.afp.modca.structuredfields.attribute;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Before;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducerTestCase;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletsTestCase;
import org.modica.afp.modca.structuredfields.types.AttributeType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedNameTestCase;

import static org.junit.Assert.assertTrue;

public class TagLogicalElementTestCase extends
        StructuredFieldWithTripletsTestCase<TagLogicalElement> {

    private TagLogicalElement sut;
    private StructuredFieldIntroducer intro;

    @Before
    public void setUp() throws MalformedURLException, UnsupportedEncodingException {
        List<Triplet> triplets = addTripletToList(
                FullyQualifiedNameTestCase.FONT_CHAR_SET_NAME_REF,
                FullyQualifiedNameTestCase.CODE_PAGE_NAME_REF);
        intro = StructuredFieldIntroducerTestCase.createGenericIntroducer(AttributeType.TLE);
        sut = new TagLogicalElement(intro, triplets);
        setMembers(sut, intro, triplets);
    }

    @Override
    public void testGetParameters() {
        assertTrue(sut.getParameters().isEmpty());
    }

}
