package org.afpparser.afp.modca.structuredfields.begin;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The Begin Document structured field names and begins the document.
 */
public final class BeginDocument extends StructuredFieldWithTriplets {

    private final String documentName;
    private final boolean docNameProvidedBySystem;

    public BeginDocument(StructuredFieldIntroducer introducer, List<Triplet> triplets, Parameters params)
            throws UnsupportedEncodingException {
        super(introducer, triplets);
        if (params.getByte() != (byte) 0xFF && params.getByte() != (byte) 0xFF) {
            documentName = params.getString(0, 8);
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

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("DocumentName", documentName);
        return params;
    }
}
