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

package org.modica.afp.modca.structuredfields.include;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.ReferenceCoordinateSystem;
import org.modica.afp.modca.common.Rotation;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.TripletHandler;

/**
 * An Include Object structured field references an object on a page or overlay. It optionally
 * contains parameters that identify the object and that specify presentation parameters such as
 * object position, size, orientation, mapping, and default color. Where the presentation parameters
 * conflict with parameters specified in the object’s environment group (OEG), the parameters in the
 * Include Object structured field override. If the referenced object is a page segment, the IOB
 * parameters override the corresponding environment group parameters on all data objects in the
 * page segment.
 */
public class IncludeObject extends StructuredFieldWithTriplets {

    private final String objName;
    private final ObjectType objType;
    private final int xoaOset;
    private final boolean useXOriginArea;
    private final int yoaOset;
    private final boolean useYOriginArea;
    private final Rotation xoaOrent;
    private final boolean useXObjectRotation;
    private final Rotation yoaOrent;
    private final boolean useYObjectRotation;
    private final int xocaOset;
    private final boolean useXOriginOffset;
    private final int yocaOset;
    private final boolean useYOriginOffset;
    private final ReferenceCoordinateSystem refCSys;

    IncludeObject(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        objName = params.getString(8);
        byte reserved = params.getByte();
        assert reserved == (byte) 0x00;
        objType = ObjectType.getValue(params.getByte());
        // setting the origin
        if (nextFewByesEqualFF(params, 3)) {
            xoaOset = 0xFFFFFF;
            useXOriginArea = true;
        } else {
            xoaOset = params.getInt(3);
            useXOriginArea = false;
        }
        if (nextFewByesEqualFF(params, 3)) {
            yoaOset = 0xFFFFFF;
            useYOriginArea = true;
        } else {
            yoaOset = params.getInt(3);
            useYOriginArea = false;
        }
        // setting the orientation
        if (nextFewByesEqualFF(params, 2)) {
            xoaOrent = null;
            useXObjectRotation = true;
        } else {
            xoaOrent = Rotation.getValue(params.getByte());
            useXObjectRotation = false;
            params.skip(1);
        }
        if (nextFewByesEqualFF(params, 2)) {
            yoaOrent = null;
            useYObjectRotation = true;
        } else {
            yoaOrent = Rotation.getValue(params.getByte());
            useYObjectRotation = false;
            params.skip(1);
        }
        if (nextFewByesEqualFF(params, 3)) {
            xocaOset = 0xFFFFFF;
            useXOriginOffset = true;
        } else {
            xocaOset = params.getInt(3);
            useXOriginOffset = false;
        }
        if (nextFewByesEqualFF(params, 3)) {
            yocaOset = 0xFFFFFF;
            useYOriginOffset = true;
        } else {
            yocaOset = params.getInt(3);
            useYOriginOffset = false;
        }
        refCSys = ReferenceCoordinateSystem.getValue(params.getByte());
    }

    private boolean nextFewByesEqualFF(Parameters params, int length) {
        byte ff = (byte) 0xFF;
        int pos = params.getPosition();
        for (int i = 0; i < length; i++) {
            if (params.getByte() != ff) {
                params.skipTo(pos);
                return false;
            }
        }
        return true;
    }

    /**
     * Identifies the type of object being referenced.
     */
    public enum ObjectType {
        /** Page segment object. */
        PAGE_SEGMENT(0x5F),
        /**
         * Other object data. The object data to be included is a paginated presentation object
         * whose format may or may not be defined by an IBM presentation architecture. The object
         * data is characterized and identified by a mandatory Object Classification (X'10')
         * triplet, which must specify the registered object-type OID for the object type and must
         * characterize the object as being a presentation object. This triplet also specifies
         * whether the object data is carried in a MO:DCA object container, whether it is unwrapped
         * object data, or whether the container structure of the object data is unknown.
         * <p>
         * This value is not used for OCA objects since they are referenced using object-specific
         * values for the ObjType parameter.
         * </p>
         * <p>
         * To ensure proper presentation of the object, the object-type OID must be supported by the
         * MO:DCA-P presentation system. This means that the object-type OID is supported by the
         * presentation server, and that it is either supported directly by the presentation device,
         * or that it can be transformed by the server into a format that is directly supported by
         * the presentation device.
         * </p>
         */
        OTHER_OBJECT_DATA(0x92),
        /** Graphics (GOCA) object with MO:DCA object syntax */
        GRAPHICS_GOCA(0xBB),
        /** Bar code (BCOCA) object with MO:DCA object syntax */
        BAR_CODE_BCOCA(0xEB),
        /** Image (IOCA) object with MO:DCA object syntax */
        IMAGE_IOCA(0xFB);

        private final byte id;

        private ObjectType(int id) {
            this.id = (byte) id;
        }

        static ObjectType getValue(byte id) {
            for (ObjectType type : ObjectType.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException(id + " is not a valid object type");
        }

    }

    /**
     * Returns the name of the object being referenced. This name may be a file name or any other
     * identifier associated with the object data.
     *
     * @return the object name
     */
    public String getObjName() {
        return objName;
    }

    /**
     * Identifies the type of object being referenced.
     *
     * @return the object type
     */
    public ObjectType getObjType() {
        return objType;
    }

    /**
     * Specifies the offset along the X axis, Xpg or Xol, of the including page or overlay
     * coordinate system to the origin of the X axis, Xoa, of the object area coordinate system. The
     * value for this parameter is expressed in terms of the number of page or overlay coordinate
     * system X-axis measurement units.
     *
     * @return the x-axis origin
     */
    public int getXoaOset() {
        return xoaOset;
    }

    /**
     * A XoaOset value of X'FFFFFF' indicates that the X-axis offset specified in the object’s OEG
     * is to be used. Therefore, the offset value (−1) is not included in the allowed range.
     * <p>
     * If the object does not specify the X-axis offset in an OEG, the architected default is
     * X'000000'.
     * </p>
     * @return whether this object uses the x-axis as its origin specified in the objects OEG
     */
    public boolean useXAxisOriginArea() {
        return useXOriginArea;
    }

    /**
     * Specifies the offset along the Y axis, Ypg or Yol, of the including page or overlay
     * coordinate system to the origin of the Y axis, Yoa, of the object area coordinate system. The
     * value for this parameter is expressed in terms of the number of page or overlay coordinate
     * system Y-axis measurement units.
     *
     * @return the y-axis origin
     */
    public int getYoaOset() {
        return yoaOset;
    }

    /**
     * A value of X'FFFFFF' indicates that the Y-axis offset specified in the object’s OEG is to be
     * used. Therefore, the offset value (−1) is not included in the allowed range.
     * <p>
     * If the object does not specify the Y-axis offset in an OEG, the architected default is
     * X'000000'.
     * </p>
     *
     * @return whether this object uses the y-axis as its origin specified in the objects OEG
     */
    public boolean useYAxisOriginArea() {
        return useYOriginArea;
    }

    /**
     * Specifies the amount of clockwise rotation of the object area’s X axis, Xoa, about its
     * defined origin relative to the X axis of the page or overlay coordinate system.
     *
     * @return the x-axis object orientation
     */
    public Rotation getXoaOrent() {
        return xoaOrent;
    }

    /**
     * A XoaOrent value of X'FFFF' indicates that the X-axis rotation specified in the object’s OEG
     * is to be used.
     * <p>
     * If the object does not specify the X-axis rotation in an OEG, the architected default is
     * X'0000' (0 degrees).
     * </p>
     *
     * @return whether this object used the x-axis rotation specified in the objects OEG
     */
    public boolean useXAxisObjectRotation() {
        return useXObjectRotation;
    }

    /**
     * Specifies the amount of clockwise rotation of the object area’s Y axis, Yoa, about its
     * defined origin relative to the X axis of the page or overlay coordinate system.
     *
     * @return the y-axis object orientation
     */
    public Rotation getYoaOrent() {
        return yoaOrent;
    }

    /**
     * A YoaOrent value of X'FFFF' indicates that the Y-axis rotation specified in the object’s OEG
     * is to be used.
     * <p>
     * If the object does not specify the Y-axis rotation in an OEG, the architected default is
     * X'2D00' (90 degrees).
     * </p>
     *
     * @return whether this object used the y-axis rotation specified in the objects OEG
     */
    public boolean useYAxisObjectRotation() {
        return useYObjectRotation;
    }

    /**
     * Used in position and position and trim mappings to specify the offset along the X axis of the
     * object area coordinate system, Xoa, to the X origin of the object content. The value for this
     * parameter is expressed in terms of the number of object area coordinate system X-axis
     * measurement units.
     *
     * @return the x-axis offset
     */
    public int getXocaOset() {
        return xocaOset;
    }

    /**
     * A XocaOset value of X'FFFFFF' indicates that the X-axis offset specified in the object’s OEG
     * is to be used. Therefore, the offset value (−1) is not included in the allowed range.
     * <p>
     * If the object does not specify the X-axis offset in an OEG, the architected default is
     * X'000000'.
     * </p>
     *
     * @return whether this object uses the x-axis offset specified in the objects OEG
     */
    public boolean useXAxisOriginOffset() {
        return useXOriginOffset;
    }

    /**
     * Used in position and position and trim mappings to specify the offset along the Y axis of the
     * object area coordinate system, Yoa, to the Y origin of the object content. The value for this
     * parameter is expressed in terms of the number of object area coordinate system Y-axis
     * measurement units.
     *
     * @return the y-axis offset
     */
    public int getYocaOset() {
        return yocaOset;
    }

    /**
     * A YocaOset value of X'FFFFFF' indicates that the Y-axis offset specified in the object’s OEG
     * is to be used. Therefore, the offset value (−1) is not included in the allowed range.
     * <p>
     * If the object does not specify the Y-axis offset in an OEG, the architected default is
     * X'000000'.
     * </p>
     *
     * @return whether this object uses the y-axis offset specified in the objects OEG
     */
    public boolean useYAxisOriginOffset() {
        return useYOriginOffset;
    }

    /**
     * Specifies the coordinate system used to position the object area.
     *
     * @return the reference coordinate system
     */
    public ReferenceCoordinateSystem getRefCSys() {
        return refCSys;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ObjectName", objName));
        params.add(new ParameterAsString("ObjectType", objType));
        params.add(new ParameterAsString("X-AxisObjectAreaOffset", xoaOset));
        params.add(new ParameterAsString("Y-AxisObjectAreaOffset", yoaOset));
        params.add(new ParameterAsString("X-AxisObjectOrientation", xoaOrent));
        params.add(new ParameterAsString("Y-AxisObjectOrientation", yoaOrent));
        params.add(new ParameterAsString("X-AxisObjectOffset", xocaOset));
        params.add(new ParameterAsString("Y-AxisObjectOffset", yocaOset));
        params.add(new ParameterAsString("ReferenceCoordSystem", refCSys));
        return params;
    }

    public static final class IOBBuilder implements Builder {
        @Override
        public IncludeObject build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return new IncludeObject(intro, TripletHandler.parseTriplet(params, 27, context), params);
        }
    }
}
