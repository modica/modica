package org.afpparser.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTriplets;
import org.afpparser.afp.modca.triplets.Triplet;

/**
 * The End Document structured field terminates the MO:DCA document data stream initiated by a Begin
 * Document structured field.
 */
public class EndDocument extends StructuredFieldWithTriplets {

    private final EndFieldName docName;

    public EndDocument(SfIntroducer introducer, List<Triplet> triplets,
            byte[] sfData) throws UnsupportedEncodingException {
        super(introducer, triplets);
        docName = new EndFieldName(sfData);
    }

    /**
     * Returns the name of the document being terminated. If a name is specified, it must match the
     * name in the most recent Begin Document structured field in the data stream or a X’01’
     * exception condition exists. If the first two bytes of DocName contain the value X'FFFF', the
     * name matches any name specified on the Begin Document structured field that initiated the
     * current definition.
     *
     * @return the Document Name
     */
    public String getDocName() {
        return docName.getName();
    }

    /**
     * Whether or not the name of this object matches any name in the BeginDocument.
     *
     * @return true if the name matches any
     */
    public boolean nameMatchesAny() {
        return docName.matchesAny();
    }

    @Override
    public String toString() {
        return getType().toString() + " DocName=" + docName;
    }
}
