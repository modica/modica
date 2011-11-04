package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * This triplet contains the name of the font typeface. This identifier corresponds to the full name
 * of the typeface as specified by the font supplier. This is the user interface name which, for
 * example, may be used for specification or selection of the font design. It is possible that it
 * does not correspond exactly to the font resource name, character content or supported sizes,
 * such as in the case of ITC Italic Bold Garamond or Monotype Times New Roman Expanded.
 */
public class FontTypefaceName extends FQNCharStringData {

    FontTypefaceName(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.font_typeface_name;
    }

}
