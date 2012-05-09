package org.modica.afp.ptoca;

import java.io.UnsupportedEncodingException;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;

/**
 * This control sequence specifies a string of code points, all of which are to be processed as
 * graphic characters. No code point within the data field is recognised as a Control Sequence
 * Prefix. The current inline position is incremented for each graphic character in the string.
 */
public class TransparentData extends ControlSequence {

    private final byte[] data;

    public TransparentData(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params, Context ctx) {
        super(csId, length, isChained);
        data = params.getByteArray(length - 2);
    }

    /**
     * Return the data in the form of a String.
     *
     * @param encoding the encoding to encode the byte array
     * @return the String form
     * @throws UnsupportedEncodingException if an error occurs encoding the String
     */
    public String getDataString(String encoding) throws UnsupportedEncodingException {
        return new String(data, encoding);
    }

    @Override
    public String getValueAsString() {
        try {
            return "\"" + getDataString("Cp500") + "\"";
        } catch (UnsupportedEncodingException uee) {
            return "TransparentData:";
        }
    }
}
