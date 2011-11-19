package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.SfIntroducer;
import org.afpparser.afp.modca.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;
import org.afpparser.common.StringUtils;

/**
 * The Begin Document structured field names and begins the document.
 */
public final class BeginDocument extends StructuredFieldWithTriplets {

    private final String documentName;
    private final boolean docNameProvidedBySystem;

    public BeginDocument(SfIntroducer introducer, List<Triplet> triplets, byte[] sfData)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
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
     * <p>
     * Whether or not the DocName is provided by the system can be ascertained from the
     * {@code docNameProvidedBySystem()} method.</p>
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
}
