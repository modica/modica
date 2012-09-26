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

package org.modica.afp.modca.structuredfields;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.modica.afp.modca.Context;
import org.modica.afp.modca.ContextImpl;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;
import org.modica.afp.modca.triplets.TripletHandler;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test case for subclasses of {@link StructuredFieldWithTripletGroup}.
 *
 * @param <T> subclass of {@link StructuredFieldWithTripletGroup}
 */
public abstract class StructuredFieldWithTripletGroupTestCase<T extends StructuredFieldWithTripletGroup>
        extends StructuredFieldTestCase<T> {
    private StructuredFieldWithTripletGroup sut;
    private RepeatingTripletGroup rGroup;

    public void setMembers(StructuredFieldWithTripletGroup sut, StructuredFieldIntroducer intro,
            RepeatingTripletGroup rGroup) {
        super.setMembers(sut, intro);
        this.sut = sut;
        this.rGroup = rGroup;
    }

    @Override
    @Test
    public void testGetters() {
        assertEquals(rGroup, sut.getTripletGroup());
        assertEquals(rGroup.size() > 0, sut.hasTripletGroup());
    }

    public static RepeatingTripletGroup createGenericRepeatingGroup() throws MalformedURLException,
            UnsupportedEncodingException {
        // I think this works... will find out soon enough
        byte[] data = ByteUtils.hexToBytes("0022"
                + "0C028600C3F0C8F2F0F0" + "C2F00C028500E3F1E5F1F0F5F0F00426000004240502");
        Context context = new ContextImpl();
        Parameters params = new Parameters(data);
        return TripletHandler.parseRepeatingGroup(params, context);
    }
}
