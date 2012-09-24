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

package org.modica.afp.modca.structuredfields.descriptor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.PresentationSpaceUnits;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * The Page Descriptor structured field specifies the size and attributes of a page or overlay
 * presentation space.
 * <p>
 * Some AFP print servers require that the measurement units in the PGD match the measurement units
 * in the Presentation Text Descriptor (PTD) when the latter is included in the AEG for a page. It
 * is therefore strongly recommended that whenever the PTD is included in the AEG, the same
 * measurement units are specified in both the PTD and PGD.
 * </p>
 * <p>
 * Note:
 * If the sum of the page or overlay origin offset and the page or overlay extent exceeds the size
 * of the including presentation space in either the X or Y direction, all of the page or overlay
 * will not fit on the including presentation space. The including presentation space in this case
 * is the medium presentation space. If an attempt is made to actually present data in the portion
 * of the page or overlay that falls outside the including presentation space, that portion of the
 * data is not presented, and a X’01’ exception condition exists.
 * </p>
 * <p>
 * Architecture Note:
 * Triplets that affect the page or overlay presentation space are processed in the order in which
 * they occur on the PGD. For example, if a Presentation Space Reset Mixing (X'70') triplet on the
 * PGD is followed by a Colour Specification (X'4E') triplet, the presentation space is coloured
 * with the colour specified in the X'4E' triplet and covers any data underneath it regardless of
 * whether the X'70' triplet specified “reset to colour of medium” or “do not reset to colour of
 * medium”. If a Colour Specification (X'4E') triplet is followed by a X'70' triplet, and if the
 * X'70' triplet specified “reset to colour of medium”, the presentation space is coloured with
 * colour of medium. If the X'70' triplet specified “do not reset to colour of medium”, the X'70'
 * triplet does not change the presentation space and it remains foreground data coloured with the
 * colour specified by the X'4E' triplet.
 * </p>
 */
public class PageDescriptor extends StructuredFieldWithTriplets {

    private final PresentationSpaceUnits xAxisBaseUnit;
    private final PresentationSpaceUnits yAxisBaseUnit;

    private final int xAxisPageUnit;
    private final int yAxisPageUnit;
    private final int xAxisPageSize;
    private final int yAxisPageSize;

    PageDescriptor(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params) {
        super(introducer, triplets);
        xAxisBaseUnit = PresentationSpaceUnits.getValue(params.getByte());
        yAxisBaseUnit = PresentationSpaceUnits.getValue(params.getByte());
        xAxisPageUnit = (int) params.getUInt(2);
        yAxisPageUnit = (int) params.getUInt(2);
        xAxisPageSize = (int) params.getUInt(3);
        yAxisPageSize = (int) params.getUInt(3);
        assert params.getByte() == 0 && params.getByte() == 0;
    }

    /**
     * Specifies the unit base for the X axis of the page or overlay coordinate system.
     *
     * @return the base unit for the x-axis
     */
    public PresentationSpaceUnits getXpgBase() {
        return xAxisBaseUnit;
    }

    /**
     * Specifies the unit base for the Y axis of the page or overlay coordinate system.
     *
     * @return the base unit for the y-axis
     */
    public PresentationSpaceUnits getYpgBase() {
        return yAxisBaseUnit;
    }

    /**
     * Specifies the number of units per unit base for the X axis of the page or overlay
     * coordinate system.
     *
     * @return the number of units on the x-axis
     */
    public int getXpgUnit() {
        return xAxisPageUnit;
    }

    /**
     * Specifies the number of units per unit base for the Y axis of the page or overlay coordinate
     * system.
     *
     * @return the number of units on the y-axis
     */
    public int getYpgUnit() {
        return yAxisPageUnit;
    }

    /**
     * Specifies the extent of the X axis of the page or overlay coordinate system. This is also
     * known as the page or overlay’s X-axis size.
     *
     * @return the extent of the x-axis
     */
    public int getXpgSize() {
        return xAxisPageSize;
    }

    /**
     * Specifies the extent of the Y axis of the page or overlay coordinate system. This is also
     * known as the page or overlay’s Y-axis size.
     *
     * @return the extent of the y-axis
     */
    public int getYpgSize() {
        return yAxisPageSize;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("X-AxisBaseUnit", xAxisBaseUnit));
        params.add(new ParameterAsString("Y-AxisBaseUnit", yAxisBaseUnit));
        params.add(new ParameterAsString("X-AxisPageUnit", xAxisPageUnit));
        params.add(new ParameterAsString("Y-AxisPageUnit", yAxisPageUnit));
        params.add(new ParameterAsString("X-AxisPageSize", xAxisPageSize));
        params.add(new ParameterAsString("Y-AxisPageSize", yAxisPageSize));
        return params;
    }

    public static final class PGDBuilder implements Builder {
        @Override
        public PageDescriptor build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new PageDescriptor(intro, TripletHandler.parseTriplet(params, 15, context), params);
        }
    }
}
