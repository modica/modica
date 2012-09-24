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

package org.modica.afp.modca.triplets;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;

/**
 * The Object Area Size triplet is used to specify the extent of an object area in the X and Y
 * directions.
 */
public class ObjectAreaSize extends Triplet {

    private static final int LENGTH = 9;
    private final int xoaSize;
    private final int yoaSize;

    public ObjectAreaSize(Parameters params) {
        byte b = params.getByte();
        assert b == (byte) 0x02;
        xoaSize = (int) params.getUInt(3);
        yoaSize = (int) params.getUInt(3);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    /**
     * Specifies the extent of the X axis of the object area coordinate system. This is also known
     * as the object area’s X axis size.
     *
     * @return the x-axis size
     */
    public int getXoaSize() {
        return xoaSize;
    }

    /**
     * Specifies the extent of the Y axis of the object area coordinate system. This is also known
     * as the object area’s Y axis size.
     *
     * @return the y-axis size
     */
    public int getYoaSize() {
        return yoaSize;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.object_area_size;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjectAreaSize)) {
            return false;
        }
        ObjectAreaSize obj = (ObjectAreaSize) o;
        return this.xoaSize == obj.xoaSize
                && this.yoaSize == obj.yoaSize;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + xoaSize;
        hashCode = 31 * hashCode + yoaSize;
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return getTid().toString() + " xoaSize=" + xoaSize + " yoaSize=" + yoaSize;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("X-AxisSize", xoaSize));
        params.add(new ParameterAsString("Y-AxisSize", yoaSize));
        return params;
    }
}
