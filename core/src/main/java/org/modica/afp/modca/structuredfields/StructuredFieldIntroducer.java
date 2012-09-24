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

import java.io.Serializable;

import org.modica.common.StringUtils;
import org.modica.parser.StructuredFieldHandler;
import org.modica.parser.StructuredFieldIntroducerHandler;

/**
 * A data object that represents a structured field and holds the data provided in the introducer.
 */
public class StructuredFieldIntroducer implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final byte CARRIAGE_CONTROL = 0x5a;

    public static final int Carriage_Control_FIELD = 1;
    public static final int SFLength_FIELD = 2;
    public static final int SFType_ID_FIELD = 3;
    public static final int Flag_Byte_FIELD = 1;
    public static final int Reserved_FIELD = 1;
    public static final int ExtLength_FIELD = 1;

    public static final int SF_Introducer_FIELD = StructuredFieldIntroducer.SFLength_FIELD
            + StructuredFieldIntroducer.SFType_ID_FIELD
            + StructuredFieldIntroducer.Flag_Byte_FIELD
            + StructuredFieldIntroducer.Reserved_FIELD;

    private final long offset;
    private final int length;
    private final StructuredFieldType type;
    private final byte flags;
    private final int extLength;

    /**
     * The introducer data for the structured field.
     *
     * @param offset the byte offset of this structured field (including the length field)
     * @param length the length of this structured field in bytes (including the length field)
     * @param type the type id
     * @param flags the bit-mapped flags
     * @param extLength the length of SFI extension data
     */
    public StructuredFieldIntroducer(long offset, int length, byte[] type, byte flags, int extLength) {
        this.offset = offset;
        this.length = length;
        this.type = StructuredFieldTypeFactory.getValue(type);
        this.flags = flags;
        this.extLength = extLength;
    }

    /**
     * Returns whether or not this structured field has SFI extension data.
     *
     * @return true if the SFI extension bit flag has been set
     */
    public boolean hasExtData() {
        return FlagField.hasSfiExtension(flags);
    }

    /**
     * Returns whether or not this structured field has segmented data.
     *
     * @return true if the segmented data bit flag has been set
     */
    public boolean hasSegmentedData() {
        return FlagField.hasSegmentedData(flags);
    }

    /**
     * Returns whether or not this structured field has data padding.
     *
     * @return true if the data padding bit flag has been set
     */
    public boolean hasDataPadding() {
        return FlagField.hasDataPadding(flags);
    }

    /**
     * The length of this structured field as specified in the introducer. This does not include SFI
     * extension data length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * The type of structured field this object represents.
     *
     * @return the structured field type
     */
    public StructuredFieldType getType() {
        return type;
    }

    /**
     * The byte offset of this structured field.
     *
     * @return the byte offset
     */
    public long getOffset() {
        return offset;
    }

    /**
     * The length of SFI extension data as specified in the introducer.
     *
     * @return the extension data length
     */
    public int getExtLength() {
        return extLength;
    }

    /**
     * The number of bytes to the next structured field, this is a summation of the length of the
     * structured field and the length of any SFI extension data. No validation is done at this
     * point to check the actual position of the next structured field.
     *
     * @return the number of bytes to the next structured field
     */
    public int bytesToNextStructuredField() {
        return length + extLength;
    }

    /**
     * Returns the byte offset of the structured field data payload
     * i.e. this offset + number of bytes in the introducer.
     *
     * @return the byte offset of the structured field data
     */
    public long getDataOffset() {
        long dataOffset = this.offset + StructuredFieldIntroducer.SF_Introducer_FIELD
                + StructuredFieldIntroducer.Carriage_Control_FIELD;
        if (hasExtData()) {
            dataOffset += getExtLength() + StructuredFieldIntroducer.ExtLength_FIELD;
        }
        return dataOffset;
    }

    /**
     * Check whether the bit flag in <code>flags</code> has the SFI extension data set to true.
     *
     * @param flags the bit flags
     * @return true if the SFI extension flag has been set
     */
    public static boolean hasSfiExtension(byte flags) {
        return FlagField.hasSfiExtension(flags);
    }

    /**
     * Given a {@link StructuredFieldHandler} this helper method will use its
     * {@link StructuredFieldType} and call the relevant method on the handler.
     * @See {@link StructuredFieldType#handleIntroducer(StructuredFieldIntroducerHandler, StructuredFieldIntroducer)}
     *
     * @param handler the introducer handler
     */
    public void handleIntroducer(StructuredFieldIntroducerHandler handler) {
        getType().handleIntroducer(handler, this);
    }

    @Override
    public String toString() {
        return StringUtils.toHex(offset, 8) + "\t" + length + "\t" + type.getName();
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (int) offset;
        hashCode = 31 * hashCode + length;
        hashCode = 31 * hashCode + type.hashCode();
        hashCode = 31 * hashCode + flags;
        hashCode = 31 * hashCode + extLength;
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructuredFieldIntroducer)) {
            return false;
        }
        StructuredFieldIntroducer sf = (StructuredFieldIntroducer) o;
        return this.offset == sf.offset && this.length == sf.length && this.type.equals(sf.type)
                && this.flags == sf.flags && this.extLength == sf.extLength;
    }

    private static enum FlagField {
        SFI_EXTENSION,
        RESERVED1,
        SEGMENTED_DATA,
        RESERVED2,
        DATA_PADDING;

        private final byte bitMask;

        private FlagField() {
            bitMask = (byte) (0x01 << this.ordinal());
        }

        static boolean hasSfiExtension(byte flags) {
            return (SFI_EXTENSION.bitMask & flags) > 0;
        }

        static boolean hasSegmentedData(byte flags) {
            return (SEGMENTED_DATA.bitMask & flags) > 0;
        }

        static boolean hasDataPadding(byte flags) {
            return (DATA_PADDING.bitMask & flags) > 0;
        }
    }
}
