package org.afpparser.afp.modca.structuredfields.position;

import org.afpparser.afp.modca.common.ReferenceCoordinateSystem;
import org.afpparser.afp.modca.common.Rotation;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.common.ByteUtils;

/**
 * The Object Area Position structured field specifies the origin and orientation of the object
 * area, and the origin and orientation of the object content within the object area.
 */
public class ObjectAreaPosition extends AbstractStructuredField {
    private final byte oaPosId;
    private final int xoaOset;
    private final int yoaOset;
    private final Rotation xoaOrent;
    private final Rotation yoaOrent;
    private final int xocaOset;
    private final int yocaOset;
    private final ReferenceCoordinateSystem refCSys;

    public ObjectAreaPosition(SfIntroducer introducer, byte[] sfData) {
        super(introducer);
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        int position = 0;
        oaPosId = sfData[position++];
        assert sfData[position] == (byte) 23;
        position++;
        xoaOset = byteUtils.bytesToSignedInt(sfData, position, 3);
        position += 3;
        yoaOset = byteUtils.bytesToSignedInt(sfData, position, 3);
        position += 3;
        xoaOrent = Rotation.getValue(sfData[position]);
        position += 2;
        yoaOrent = Rotation.getValue(sfData[position]);
        position += 2;
        assert sfData[position] == (byte) 0x00;
        position++;
        xocaOset = byteUtils.bytesToSignedInt(sfData, position, 3);
        position += 3;
        yocaOset = byteUtils.bytesToSignedInt(sfData, position, 3);
        position += 3;
        assert sfData[position] == (byte) 0x00; assert sfData[position + 1] == (byte) 0x00;
        position +=2;
        assert sfData[position] == (byte) 0x2D; assert sfData[position + 1] == (byte) 0x00;
        position +=2;
        refCSys = ReferenceCoordinateSystem.getValue(sfData[position]);
    }

    /**
     * Specifies an identifier for this Object Area Position structured field that is unique within
     * the environment group. It is used to associate the Object Area Position structured field with
     * the Object Area Descriptor structured field.
     *
     * @return the Object Area Position Id
     */
    public byte getOaPosId() {
        return oaPosId;
    }

    /**
     * Specifies the offset along the X axis, Xpg or Xol, of the referenced coordinate system to the
     * origin of the X axis, Xoa, for the object area coordinate system. The value for this
     * parameter is expressed in terms of the number of referenced coordinate system X-axis
     * measurement units. The reference coordinate system is described below under RefCSys
     *
     * @return the x-axis origin
     */
    public int getXoaOset() {
        return xoaOset;
    }

    /**
     * Specifies the offset along the Y axis, Ypg or Yol, of the referenced coordinate system to the
     * origin of the Y axis, Yoa, for the object area coordinate system. The value for this
     * parameter is expressed in terms of the number of referenced coordinate system Y-axis
     * measurement units. The reference coordinate system is described below under RefCSys.
     *
     * @return the y-axis origin
     */
    public int getYoaOset() {
        return yoaOset;
    }

    /**
     * Specifies the amount of clockwise rotation of the object area’s X  axis, Xoa, about its
     * defined origin relative to the X axis of the reference coordinate system.
     *
     * @return the x-axis orientation
     */
    public Rotation getXoaOrent() {
        return xoaOrent;
    }

    /**
     * Specifies the amount of clockwise rotation of the object area’s Y  axis, Yoa, about its
     * defined origin relative to the X axis of the reference coordinate system.
     *
     * @return the y-axis orientation
     */
    public Rotation getYoaOrent() {
        return yoaOrent;
    }

    /**
     * Specifies the offset along the X axis of the object area coordinate system, Xoa, to the X
     * origin of the object content. The value for this parameter is expressed in terms of the
     * number of object area coordinate system X-axis measurement units.
     *
     * @return the x-axis offset
     */
    public int getXocaOset() {
        return xocaOset;
    }

    /**
     * Specifies the offset along the Y axis of the object area coordinate system, Yoa, to the Y
     * origin of the object content. The value for this parameter is expressed in terms of the
     * number of object area coordinate system Y-axis measurement units.
     *
     * @return the y-axis offset
     */
    public int getYocaOset() {
        return yocaOset;
    }

    /**
     * Specifies the amount of rotation of the object content’s X axis about  its defined origin
     * relative to the X axis of the object area coordinate system.
     *
     * @return the rotation of the x-axis from the x-axis
     */
    public Rotation getXocaOrent() {
        return Rotation.ZERO;
    }

    /**
     * Specifies the amount of rotation of the object content’s Y axis about its defined origin
     * relative to the X axis of the object area coordinate system.
     *
     * @return the rotation of the y-axis from the x-axis
     */
    public Rotation getYocaOrent() {
        return Rotation.NINETY;
    }

    /**
     * Specifies the coordinate system and origin used to position the object area.
     *
     * @return the reference coordinate system
     */
    public ReferenceCoordinateSystem getRefCSys() {
        return refCSys;
    }

    @Override
    public String toString() {
        return getType().toString() + " xoaOset=" + xoaOset + " yoaOset=" + yoaOset
                + " xocaOset=" + xocaOset + " yocaOset=" + yocaOset;
    }
}
