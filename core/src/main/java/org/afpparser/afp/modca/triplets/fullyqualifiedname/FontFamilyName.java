package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * This triplet contains the name of the font family. This identifier corresponds to the family of
 * the font design. For example, "Times New Roman" is the family name for the Monotype Times New
 * Roman Expanded font design. The family also appears as a substring in the typeface name.
 */
public class FontFamilyName extends FQNCharStringData {

    FontFamilyName(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.font_family_name;
    }
}
