package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.AbstractStructuredField;
import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Document structured field names and begins the document.
 */
public class BeginDocument extends AbstractStructuredField {

    private final String documentName;
    private final boolean docNameProvidedBySystem;

    public BeginDocument(SfIntroducer introducer, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer);
        if (sfData != null && (sfData[0] != 0xFF && sfData[1] != 0xFF)) {
            documentName = StringUtils.bytesToCp500(sfData);
            docNameProvidedBySystem = false;
        } else {
            docNameProvidedBySystem = true;
            documentName = null;
        }
    }

    /**
     * The document name is the name of the document described by the data stream. If a Fully
     * Qualified Name type X’01’ (Replace First GID) appears in this structured field, the name
     * specified in this parameter is ignored and the GID provided by the triplet is used instead.
     * If the value of the first two bytes of DocName are X'FFFF', the processing system provides
     * the document name.
     *
     * @return the document name
     */
    public String getDocName() {
        return documentName;
    }

    /**
     * Returns true if the document name is provided by the system.
     *
     * @return true if the document name is provided by the system
     */
    public boolean docNameProvidedBySystem() {
        return docNameProvidedBySystem;
    }

    @Override
    public String toString() {
        return "docName=" + documentName;
    }

    @Override
    public boolean hasTriplets() {
        return false;
    }

    @Override
    public List<Triplet> getTriplets() {
        return null;
    }

}
