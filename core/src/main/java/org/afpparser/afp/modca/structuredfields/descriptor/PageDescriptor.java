package org.afpparser.afp.modca.structuredfields.descriptor;

import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.ByteUtils;

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
 */
public class PageDescriptor extends StructuredFieldWithTriplets {

    private final PageUnit xAxisBaseUnit;
    private final PageUnit yAxisBaseUnit;

    private final int xAxisPageUnit;
    private final int yAxisPageUnit;
    private final int xAxisPageSize;
    private final int yAxisPageSize;

    public PageDescriptor(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData) {
        super(introducer, triplets);
        int position = 0;
        xAxisBaseUnit = PageUnit.getValue(sfData[position++]);
        yAxisBaseUnit = PageUnit.getValue(sfData[position++]);
        ByteUtils byteUtils = ByteUtils.newLittleEndianUtils();
        xAxisPageUnit = byteUtils.bytesToUnsignedInt(sfData, position, 2);
        position += 2;
        yAxisPageUnit = byteUtils.bytesToUnsignedInt(sfData, position, 2);
        position += 2;
        xAxisPageSize = byteUtils.bytesToUnsignedInt(sfData, position, 3);
        position += 3;
        yAxisPageSize = byteUtils.bytesToUnsignedInt(sfData, position, 3);
        position += 3;
        assert sfData[position++] == 0 && sfData[position] == 0;
    }

    /**
     * An enumerated type that describes the base units for the page or the overlay coordinate
     * system.
     */
    public enum PageUnit {
        /** Units measured in units of 10 inches */
        INCHES_10(0x00),
        /** Units measured in units of 10 centimetres */
        CENTIMETRE_10(0x01);

        private final byte id;

        private PageUnit(int id) {
            this.id = (byte) id;
        }

        private static PageUnit getValue(byte id) {
            for (PageUnit unit : PageUnit.values()) {
                if (unit.id == id) {
                    return unit;
                }
            }
            throw new IllegalArgumentException("Byte value:" + id + " is not a valid PageUnit");
        }
    }

    /**
     * Specifies the unit base for the X axis of the page or overlay coordinate system.
     *
     * @return the base unit for the x-axis
     */
    public PageUnit getXpgBase() {
        return xAxisBaseUnit;
    }

    /**
     * Specifies the unit base for the Y axis of the page or overlay coordinate system.
     *
     * @return the base unit for the y-axis
     */
    public PageUnit getYpgBase() {
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
    public String toString() {
        return "Page Descriptor pageWidth=" + xAxisPageSize + " pageHeight=" + yAxisPageSize;
    }
}
