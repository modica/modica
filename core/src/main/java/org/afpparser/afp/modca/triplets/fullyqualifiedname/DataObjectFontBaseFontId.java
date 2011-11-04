package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains a GID reference to a data-object font file that defines a base font. In
 * font linking, the base font is the font that is referenced in the data stream and that is
 * processed first. The GID is a full font name that has been assigned to the font.
 */
public class DataObjectFontBaseFontId extends FQNCharStringData {
    public DataObjectFontBaseFontId(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.data_object_font_base_font_id;
    }
}
