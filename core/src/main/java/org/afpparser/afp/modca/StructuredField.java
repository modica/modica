package org.afpparser.afp.modca;

public class StructuredField {
    private final int length;
    private final StructuredFieldType type;
    private final byte flags;
    
    private short extLength;
    private byte[] extData;

    public StructuredField(int length, byte[] type, byte flags) {
        this.length = length;
        this.type = StructuredFieldType.getValue(type);
        this.flags = flags;
    }

    public StructuredField(short length, byte[] type, byte flags, short extLength, byte[] extData) {
        this(length, type, flags);
        this.extLength = extLength;
        this.extData = extData;
    }

    public boolean hasExtData() {
        return FlagField.hasSfiExtension(flags);
    }

    public boolean hasSegmentedData() {
        return FlagField.hasSegmentedData(flags);
    }

    public boolean hasDataPadding() {
        return FlagField.hasDataPadding(flags);
    }

    public String toString() {
        return type.toString();
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
