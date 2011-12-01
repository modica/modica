package org.afpparser.afp.modca.ioca;


/**
 * Set IOCA Function Set Identification self-defining field.
 */
public class IocaFunctionSetId implements SelfDefiningField {

    private final FunctionSet fs;
    private static int LENGTH = 3;

    public IocaFunctionSetId(byte[] data, int position) {
        int byteIndex = position;
        assert data[byteIndex] == (byte) 0x02;
        byteIndex++;
        assert data[byteIndex] == (byte) 0x01;
        byteIndex++;
        fs = getFunctionSet(data[byteIndex]);
    }

    private FunctionSet getFunctionSet(byte id) {
        switch (id) {
        case 0x0A:
            return FunctionSet.FS_10;
        case 0x0B:
            return FunctionSet.FS_11;
        case 0x28:
            return FunctionSet.FS_40;
        case 0x2A:
            return FunctionSet.FS_42;
        case 0x2D:
            return FunctionSet.FS_45;
        default:
            throw new IllegalArgumentException(id + " is not a valid IOCA function set");
        }
    }

    /**
     * Return the length of this field.
     *
     * @return the length
     */
    public int getLength() {
        return LENGTH;
    }

    /**
     * Returns the IOCA function set.
     *
     * @return the function set
     */
    public FunctionSet getFunctionSet() {
        return fs;
    }

    @Override
    public byte getId() {
        return (byte) 0xF7;
    }

    @Override
    public String toString() {
        return "IOCAFunctionSetId functionset=" + fs;
    }
}
