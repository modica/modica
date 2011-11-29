package org.afpparser.afp.modca.triplets;

import org.afpparser.common.ByteUtils;

/**
 * The Object Area Size triplet is used to specify the extent of an object area in the X and Y
 * directions.
 */
public class ObjectAreaSize extends Triplet {

    private static final int LENGTH = 9;
    private final int xoaSize;
    private final int yoaSize;

    public ObjectAreaSize(byte[] data, int position) {
        int byteIndex = position;
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        assert data[byteIndex] == (byte) 0x02;
        byteIndex++;
        xoaSize = byteUtils.bytesToUnsignedInt(data, byteIndex, 3);
        byteIndex += 3;
        yoaSize = byteUtils.bytesToUnsignedInt(data, byteIndex, 3);
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
}
