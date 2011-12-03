package org.afpparser.afp.modca.structuredfields.descriptor;

import java.io.UnsupportedEncodingException;

import org.afpparser.afp.modca.common.EncodingScheme;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.common.ByteUtils;
import org.afpparser.common.StringUtils;

/**
 * The Code Page Descriptor (CPD) structured field describes the code page.
 */
public class CodePageDescriptor extends AbstractStructuredField {

    private final String cpDesc;
    private static final int GCGIDLEN = 8;
    private final int numCdPts;
    private final int gcsgid;
    private final int cpgid;
    private final EncodingScheme encScheme;

    public CodePageDescriptor(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer);
        int byteIndex = 0;
        ByteUtils byteUtils = ByteUtils.getLittleEndianUtils();
        cpDesc = StringUtils.bytesToCp500(sfData, byteIndex, 32);
        byteIndex += 32;
        assert byteUtils.bytesToSignedInt(sfData, byteIndex, 2) == GCGIDLEN;
        byteIndex += 2;

        numCdPts = byteUtils.bytesToSignedInt(sfData, byteIndex, 4);
        byteIndex += 4;

        gcsgid = byteUtils.bytesToSignedInt(sfData, byteIndex, 2);
        byteIndex += 2;

        cpgid = byteUtils.bytesToSignedInt(sfData, byteIndex, 2);
        byteIndex += 2;

        encScheme = EncodingScheme.getValue(sfData[byteIndex]);
    }

    /**
     * The character string assigned to this field is intended to aid the end user, who may need to
     * edit the code page, in identifying the set of characters represented by the code page. The
     * name or title used should correspond to the name assigned by the IBM registration authority
     * when the code page was registered. Though it may be any name or title that has meaning to
     * the creator or editor of the code page.
     *
     * @return code page descriptor
     */
    public String getCpDesc() {
        return cpDesc;
    }

    /**
     * The Number of Coded Graphic Characters Assigned parameter specifies the number of graphic
     * characters (code points in one or more sections) that have been assigned in a code page.
     *
     * @return the number of coded graphic characters
     */
    public int getNumCdPts() {
        return numCdPts;
    }

    /**
     * Returns the Graphic Character Set Global Identifier (GCSGID) parameter specifies the number
     * assigned to a graphic character set, which identifies the set of graphic characters contained
     * in the font resource. The Graphic Character Set Global ID numbers that are supported, are
     * specified in IBM product documentation.
     *
     * @return the GCSGID
     */
    public int getGcsgid() {
        return gcsgid;
    }

    /**
     * Returns the Code Page Global Identifier (CPGID) parameter specifies the number assigned to a
     * code page. The code page numbers that are supported are specified in IBM product
     * documentation.
     *
     * @return the CPGID
     */
    public int getCpgid() {
        return cpgid;
    }

    /**
     * Returns the Encoding Scheme.
     *
     * @return the encoding scheme
     */
    public EncodingScheme getEncodingScheme() {
        return encScheme;
    }

    @Override
    public String toString() {
        return getType().toString() + " gcsgid=" + gcsgid + " cpgid=" + cpgid
                + " encoding=" + encScheme;
    }
}
