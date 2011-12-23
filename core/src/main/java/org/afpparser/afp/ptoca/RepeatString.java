package org.afpparser.afp.ptoca;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies a string of bytes that is to be processed entirely as graphic
 * character code points. No code point is recognized as a Control Sequence Prefix. Current inline
 * position is incremented for each graphic character specified by a code point in the string. The
 * baseline position is not changed.
 */
public class RepeatString extends ControlSequence {

    private final int repeatLength;
    private final byte[] repeatData;

    public RepeatString(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        repeatLength = (int) params.getUInt(2);
        if (getLength() > 4) {
            repeatData = params.getByteArray(getLength() - 4);
        } else {
            repeatData = null;
        }
    }

    /**
     * The length of bytes that the repeat data is repeated to fill.
     *
     * @return the repeat length
     */
    public int getRepeatLength() {
        return repeatLength;
    }

    /**
     * The data to repeat in the form of a String.
     *
     * @param encoding the encoding of the String
     * @return the String that is to be repeated
     * @throws UnsupportedEncodingException if an error occurs encoding the String.
     */
    public String getRepeatDataString(String encoding) throws UnsupportedEncodingException {
        return new String(repeatData, encoding);
    }

    @Override
    public String getValueAsString() {
        return "";
    }
}
