package org.afpparser.afp.ptoca;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.Parameters;

/**
 * This control sequence specifies a string of bytes that are to be ignored.
 */
public class NoOperation extends ControlSequence {

    private final byte[] bytes;

    public NoOperation(ControlSequenceIdentifier csId, int length, boolean isChained,
            Parameters params) {
        super(csId, length, isChained);
        bytes = params.getByteArray(getLength() - 2);
    }

    /**
     * The string of bytes this NoOp wraps in String form.
     *
     * @param encoding the encoding for the bytes
     * @return the String form
     * @throws UnsupportedEncodingException if an encoding error occurs
     */
    public String getCommentAsString(String encoding) throws UnsupportedEncodingException {
        return new String(bytes, encoding);
    }

    @Override
    public String getValueAsString() {
        try {
            return new String(bytes, "Cp500");
        } catch (UnsupportedEncodingException uee) {
            return "";
        }
    }

}
