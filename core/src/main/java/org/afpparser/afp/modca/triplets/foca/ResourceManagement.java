package org.afpparser.afp.modca.triplets.foca;

import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.afp.modca.triplets.TripletIdentifiers;
import org.afpparser.common.ByteUtils;

/**
 * An abstract class for CRCResourceManagement and FontResourceManagement triplets.
 */
public abstract class ResourceManagement extends Triplet {

    private static final int LENGTH = 6;

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public TripletIdentifiers getTid() {
        return TripletIdentifiers.resource_management;
    }

    public abstract int getResourceManagementValue();

    /**
     * This triplet provides resource management information such as a Public/Private flag and a
     * retired Cyclic Redundancy Check (CRC) value, to be used in comparing two resource objects
     * that should be identical.
     */
    public static final class CRCResourceManagement extends ResourceManagement {

        private final int rmValue;
        private final boolean isPublic;

        public CRCResourceManagement(byte[] data, int position) {
            int byteIndex = position;
            assert data[byteIndex] == (byte) 0x01;
            byteIndex++;
            rmValue = ByteUtils.getLittleEndianUtils().bytesToSignedInt(data, byteIndex, 2);
            byteIndex++;
            isPublic = (data[byteIndex] & 0x01) > 0;
        }

        /**
         * Retired for Resource Management Value. This field is retired for PSF/MVS, PSF/VSE, and
         * PSF/400 private use only. A constant value of zero (0) should be used in this field for
         * all other applications.
         * <p>
         * The CRC is a numeric value calculated by the APSRMARK utility and used by the Remote
         * PrintManager to map objects to locations in its resource library.
         * </p>
         * <p>
         * The CRC Resource Management format can exist at the same time in the same BCP structured
         * field with the Font Resource Management format.
         * <p>
         *
         * @return the resource management value
         */
        @Override
        public int getResourceManagementValue() {
            return rmValue;
        }

        /**
         * Indicates whether or not this font resource contains data that is privately owned or
         * protected by a license agreement.
         *
         * @return whether this font resource contains licensed data
         */
        public boolean isPublic() {
            return isPublic;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CRCResourceManagement)) {
                return false;
            }
            CRCResourceManagement obj = (CRCResourceManagement) o;
            return this.rmValue == obj.rmValue
                    && this.isPublic == obj.isPublic;
        }

        @Override
        public int hashCode() {
            int hashCode = 17;
            hashCode = 31 * hashCode + getTid().hashCode();
            hashCode = 31 * hashCode + rmValue;
            hashCode = 31 * hashCode + (isPublic ? 1 : 17);
            return hashCode;
        }

        @Override
        public String toString() {
            return getTid() + " type1, isPublic=" + isPublic;
        }
    }

    /**
     * This triplet is retired for PSF/MVS, PSF/VSE, and PSF/400 private use only.
     * <p>
     * This triplet, like the Type 1 triplet, provides additional information for comparing two
     * resource objects that should be identical.
     * </p>
     */
    public static final class FontResourceManagement extends ResourceManagement {

        private final int rmValue;
        private final int year;
        private final int day;
        private final int hour;
        private final int minute;
        private final int second;
        private final int decSec;

        public FontResourceManagement(byte[] data, int position) {
            int byteIndex = position;
            ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
            assert data[byteIndex] == (byte) 0x02;
            byteIndex++;
            rmValue = byteUtils.bytesToUnsignedInt(data, byteIndex, 4);
            byteIndex += 4;
            byte temp = data[byteIndex++];
            int yearValue = 0;
            if (temp == (byte) 0x40) {
                yearValue = 1900;
            } else {
                yearValue = 2000 + (temp & 0x0F);
            }
            yearValue += convertToInt(data, byteIndex, 2);
            byteIndex += 2;
            year = yearValue;
            day = convertToInt(data, byteIndex, 3);
            byteIndex += 3;
            hour = convertToInt(data, byteIndex, 2);
            byteIndex += 2;
            minute = convertToInt(data, byteIndex, 2);
            byteIndex += 2;
            second = convertToInt(data, byteIndex, 2);
            byteIndex += 2;
            decSec = convertToInt(data, byteIndex, 2);
            byteIndex += 2;
        }

        private int convertToInt(byte[] data, int position, int length) {
            int value = 0;
            for (int i = 0; i < length; i++) {
                value = (data[position + i] & 0x0F) * (10 * (length - i));
            }
            return value;
        }

        /**
         * Returns the Resource Management Value. The Engineering Change (EC) information is
         * provided by the APSRMARK utility and used by the Remote PrintManager to manage resident
         * fonts.
         * <p>
         * The Font Resource Management format can exist at the same time in the same BCP structured
         * field with the CRC Resource Management format.
         * </p>
         *
         * @return the resource management value
         */
        @Override
        public int getResourceManagementValue() {
            return rmValue;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof FontResourceManagement)) {
                return false;
            }
            FontResourceManagement obj = (FontResourceManagement) o;
            return this.rmValue == obj.rmValue
                    && this.year == obj.year
                    && this.day == obj.day
                    && this.hour == obj.hour
                    && this.minute == obj.minute
                    && this.second == obj.second
                    && this.decSec == obj.decSec;
        }

        @Override
        public int hashCode() {
            int hashCode = 17;
            hashCode = 31 * hashCode + getTid().hashCode();
            hashCode = 31 * hashCode + rmValue;
            hashCode = 31 * hashCode + year;
            hashCode = 31 * hashCode + day;
            hashCode = 31 * hashCode + hour;
            hashCode = 31 * hashCode + minute;
            hashCode = 31 * hashCode + second;
            hashCode = 31 * hashCode + decSec;
            return hashCode;
        }

        @Override
        public String toString() {
            return getTid() + " type2";
        }
    }

    public static ResourceManagement parse(byte[] data, int position) {
        switch (data[position]) {
        case 0x01:
            return new CRCResourceManagement(data, position);
        case 0x02:
            return new FontResourceManagement(data, position);
        default:
            throw new IllegalArgumentException(data[position] + " is not a valid ResourceManagement value");
        }

    }
}
