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
import org.modica.common.ByteUtils;

/**
 * The Object Function Set Specification triplet is used to specify the Object Content Architecture
 * (OCA) level for an object in a MO:DCA-P IS/1 data stream.
 */
public class ObjectFunctionSetSpecification extends Triplet {

    private final int length;
    private final ObjectType objType;
    private final byte archVrsn;
    private final int dcaFnSet;
    private final OcaFunctionSet ocaFnSet;

    public ObjectFunctionSetSpecification(Parameters params, int length) {
        this.length = length;
        objType = ObjectType.getValue(params.getByte());
        archVrsn = params.getByte();
        dcaFnSet = (int) params.getUInt(2);
        ocaFnSet = OcaFunctionSet.getValue((int) params.getUInt(2));
        // the length and triplet id fields are included in the length
        params.skip(length - 6 - 2);
    }

    /**
     * Specifies the object for which a function set is being defined.
     */
    public enum ObjectType {
        PRESENTATION_TEXT(0x02),
        GRAPHICS(0x03),
        RETIRED_OBJECT(0x05),
        IMAGE(0x06),
        FONT_CHARACTER_SET_OBJECT(0x40),
        CODE_PAGE_OBJCT(0x41),
        CODED_FONT_OBJCT(0x42),
        OBJECT_CONTAINER(0x92),
        DOCUMENT_OBJECT(0xA8),
        PAGE_SEGMENT_OBJECT(0xFB),
        OVERLAY_OBJECT(0xFC),
        PAGEDEF_OBJECT(0xFD),
        FORMDEF_OBJECT(0xFE);

        private final byte id;

        private ObjectType(int id) {
            this.id = (byte) id;
        }

        private static ObjectType getValue(byte id) {
            for (ObjectType type : ObjectType.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Object type id:" + id + " is not valid.");
        }
    }

    /**
     * Specifies the function set of the OCA defined by the ObjType parameter. The presence of this
     * parameter containing the value X'0000' indicates that at least one object from the base
     * function set is present in the data stream.
     */
    public enum OcaFunctionSet {
        PRESENTATION_TEXT_DATA_PT1(0x0000),
        GRAPHICS_DATA_DR2V0(0x4000),
        IMAGE_DATA_IOCA_FS10(0x8000);

        private final int id;

        private OcaFunctionSet(int id) {
            this.id = id;
        }

        private static OcaFunctionSet getValue(int id) {
            for (OcaFunctionSet fs : OcaFunctionSet.values()) {
                if (fs.id == id) {
                    return fs;
                }
            }
            throw new IllegalArgumentException("Oca Function set id:" + id + " is not valid.");
        }
    }

    /**
     * Returns the object for which a function set is being defined.
     *
     * @return the object type
     */
    public ObjectType getObjType() {
        return objType;
    }

    /**
     * Specifies the architecture level of the OCA.
     *
     * @return the architecture level
     */
    public byte getArchVersion() {
        return archVrsn;
    }

    /**
     * Returns the function set for the group of MO:DCA constructs identified by the ObjType
     * parameter.
     *
     * @return the function set for the group of MO:DCA constructs
     */
    public int getDcaFunctionSet() {
        return dcaFnSet;
    }

    /**
     * Returns the function set of the OCA defined by the ObjType parameter.
     *
     * @return the OCA function set
     */
    public OcaFunctionSet getOcaFunctionSet() {
        return ocaFnSet;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.object_function_set_specification;
    }

    @Override
    public String toString() {
        return "Object Fn Set ObjType=" + objType.toString() + " OCAFnSet=" + ocaFnSet.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjectFunctionSetSpecification)) {
            return false;
        }
        ObjectFunctionSetSpecification obj = (ObjectFunctionSetSpecification) o;
        return this.length == obj.length
                && this.objType == obj.objType
                && this.archVrsn == obj.archVrsn
                && this.dcaFnSet == obj.dcaFnSet
                && this.ocaFnSet == obj.ocaFnSet;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + objType.hashCode();
        hashCode = 31 * hashCode + archVrsn;
        hashCode = 31 * hashCode + dcaFnSet;
        hashCode = 31 * hashCode + ocaFnSet.hashCode();
        hashCode = 31 * hashCode + getTid().hashCode();
        return hashCode;
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("ObjectType", objType));
        params.add(new ParameterAsString("ArchVersion", ByteUtils.bytesToHex(archVrsn)));
        params.add(new ParameterAsString("MODCAFunctionSet", dcaFnSet));
        params.add(new ParameterAsString("OCAFuntionSet", ocaFnSet));
        return params;
    }
}
