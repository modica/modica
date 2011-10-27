package org.afpparser.afp.modca;


public class StructuredField {
    private final long offset;
    private final int length;
    private final StructuredFieldType type;
    private final byte flags;
    private final int extLength;

    public StructuredField(long offset, int length, byte[] type, byte flags, int extLength) {
        this.offset = offset;
        this.length = length;
        this.type = StructuredFieldType.getValue(type);
        this.flags = flags;
        this.extLength = extLength;
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

    public int getLength() {
        return length;
    }

    public long getOffset() {
        return offset;
    }

    public int getExtLength() {
        return extLength;
    }

    public int bytesToNextStructuredField() {
        return length + extLength;
    }

    @Override
    public String toString() {
        return type.getFullName();
    }

    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (int) offset;
        hashCode = 31 * hashCode + length;
        hashCode = 31 * hashCode + type.hashCode();
        hashCode = 31 * hashCode + flags;
        hashCode = 31 * hashCode + extLength;
        return hashCode();
    }

    public static boolean hasSfiExtension(byte flags) {
        return FlagField.hasSfiExtension(flags);
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

