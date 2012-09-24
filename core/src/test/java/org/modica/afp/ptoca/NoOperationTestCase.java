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

package org.modica.afp.ptoca;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.modica.afp.modca.Parameters;

import static org.junit.Assert.assertEquals;

/**
 * Test case for {@link NoOperation}.
 */
public class NoOperationTestCase extends ControlSequenceTestCase<NoOperation> {
    private NoOperation sut;
    private final String comment = "This is a test comment.";

    @Before
    public void setUp() throws UnsupportedEncodingException {
        Parameters params = new Parameters(comment.getBytes("Cp500"));
        ControlSequenceIdentifier expectedCsId = ControlSequenceIdentifier.NO_OPERATION;
        int length = 25;
        boolean isChained = true;

        sut = new NoOperation(expectedCsId, length, isChained, params);
        setMembers(sut, expectedCsId, isChained, length);
    }

    @Test
    public void testGetterMethods() throws UnsupportedEncodingException {
        assertEquals(comment, sut.getCommentAsString("Cp500"));

        assertEquals(comment, sut.getValueAsString());
    }
}
