package org.afpparser.afp.modca.structuredfields.descriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.ioca.IocaFunctionSetId;
import org.afpparser.afp.modca.ioca.SelfDefiningField;
import org.afpparser.afp.modca.ioca.SetBilevelImageColor;
import org.afpparser.afp.modca.ioca.SetExtendedBilevelImageColor;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.common.ByteUtils;

/**
 * The Image Data Descriptor structured field contains the descriptor data for an image data object.
 */
public class ImageDataDescriptor extends AbstractStructuredField {

    private final int xResol;
    private final int yResol;
    private final int xSize;
    private final int ySize;
    private final List<SelfDefiningField> selfDefiningFields;

    public ImageDataDescriptor(SfIntroducer introducer, byte[] sfData) {
        super(introducer);
        int byteIndex = 0;
        assert sfData[byteIndex] == (byte) 0x00;
        byteIndex++;
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        xResol = byteUtils.bytesToUnsignedInt(sfData, byteIndex, 2);
        byteIndex += 2;
        yResol = byteUtils.bytesToUnsignedInt(sfData, byteIndex, 2);
        byteIndex += 2;
        xSize = byteUtils.bytesToUnsignedInt(sfData, byteIndex, 2);
        byteIndex += 2;
        ySize = byteUtils.bytesToUnsignedInt(sfData, byteIndex, 2);
        byteIndex += 2;
        selfDefiningFields = getFields(sfData, byteIndex);
    }

    private List<SelfDefiningField> getFields(byte[] sfData, int position) {
        int byteIndex = position;
        List<SelfDefiningField> selfDefiningFields = new ArrayList<SelfDefiningField>();
        while (byteIndex < sfData.length) {
            SelfDefiningField sdf = null;
            switch (sfData[byteIndex++]) {
            case (byte) 0xF7:
                sdf = new IocaFunctionSetId(sfData, byteIndex);
                break;
            case (byte) 0xF6:
                sdf = new SetBilevelImageColor(sfData, byteIndex);
                break;
            case (byte) 0xF4:
                sdf = new SetExtendedBilevelImageColor(sfData, byteIndex);
                break;
            default:
                throw new IllegalArgumentException("Illegal self defining field given.");
            }
            selfDefiningFields.add(sdf);
            byteIndex += sdf.getLength();
        }
        return selfDefiningFields;
    }

    /**
     * Horizontal resolution in image points per unit base.
     *
     * @return the x-axis resolution
     */
    public int getXResol() {
        return xResol;
    }

    /**
     * Vertical resolution in image points per unit base.
     *
     * @return the y-axis resolution
     */
    public int getYResol() {
        return yResol;
    }

    /**
     * Horizontal size of the image presentation space in image points.
     *
     * @return the x-axis size
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * Vertical size of the image presentation space in image points.
     *
     * @return the y-axis size
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * The self defining fields that this object wraps.
     *
     * @return the fields
     */
    public List<SelfDefiningField> getSelfDefiningFields() {
        return Collections.unmodifiableList(selfDefiningFields);
    }

    private String sdfToString() {
        StringBuilder sb = new StringBuilder();
        for (SelfDefiningField sdf : selfDefiningFields) {
            sb.append("\t" + sdf.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getType().getName() + " xSize=" + xSize + " ySize=" + ySize + sdfToString();
    }
}
