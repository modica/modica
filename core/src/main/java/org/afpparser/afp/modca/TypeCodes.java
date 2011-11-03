package org.afpparser.afp.modca;


/**
 * Structured fields have representative type codes within their identity bytes. These enumerate
 * the various types of structured fields available.
 */
public enum TypeCodes {
    Attribute(0xA0),
    CopyCount(0xA2),
    Descriptor(0xA6),
    Control(0xA7),
    Begin(0xA8),
    End(0xA9),
    Map(0xAB),
    Position(0xAC),
    Process(0xAD),
    Include(0xAF),
    Table(0xB0),
    Migration(0xB1),
    Variable(0xB2),
    Link(0xB4),
    Data(0xEE),
    Unknown(0x00);
    private final byte typeCode;

    private TypeCodes(int typeCode) {
        this.typeCode = (byte) typeCode;
    }

    /**
     * The byte value of the type code.
     *
     * @return the type code byte
     */
    public byte getValue() {
        return typeCode;
    }

    public byte[] getIdForType(byte type) {
        return new byte[] { (byte) 0xD3, typeCode, type };
    }

    public byte[] getIdForType(CategoryCodes categoryCode) {
        return new byte[] { (byte) 0xD3, typeCode, categoryCode.getCode() };
    }
}
