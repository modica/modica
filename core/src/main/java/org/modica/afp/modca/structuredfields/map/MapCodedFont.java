package org.modica.afp.modca.structuredfields.map;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Context.ContextType;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;
import org.modica.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.modica.afp.modca.triplets.RepeatingTripletGroup;
import org.modica.afp.modca.triplets.ResourceLocalId;
import org.modica.afp.modca.triplets.ResourceLocalId.ResourceType;
import org.modica.afp.modca.triplets.Triplet;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNCharStringData;
import org.modica.afp.modca.triplets.fullyqualifiedname.FQNType;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedName;

/**
 * The Map Coded Font structured field maps a unique coded font resource local ID, which may be
 * embedded one or more times within an object’s data and descriptor, to the identifier of a coded
 * font resource object. This identifier may be specified in one of the following formats:
 * <p> - A coded font Global Resource Identifier (GRID)</p>
 * <p> - A coded font name</p>
 * <p> - A combination of code page name and font character set name</p>
 * Additionally, the Map Coded Font structured field specifies a set of resource attributes for the
 * coded font.
 */
public class MapCodedFont extends StructuredFieldWithTripletGroup {

    private final Map<Byte, CharacterSetCodePage> fontMappings = new HashMap<Byte, CharacterSetCodePage>();

    public MapCodedFont(StructuredFieldIntroducer introducer, RepeatingTripletGroup tripletGroup,
            Context ctx) throws UnsupportedEncodingException, MalformedURLException {
        super(introducer, tripletGroup);
        handleFontMappings();
        ctx.put(ContextType.MODCA_MAP_CODED_FONT, this);
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        return params;
    }

    private void handleFontMappings() {
        for (List<Triplet> triplets : getTripletGroup()) {
            String characterSet = null;
            String codePage = null;
            byte resourceId = 0;
            for (Triplet triplet : triplets) {
                switch (triplet.getTid()) {
                case fully_qualified_name:
                    FullyQualifiedName fqn = (FullyQualifiedName) triplet;
                    if (fqn.getFQNType() == FQNType.font_charset_name_ref) {
                        characterSet = ((FQNCharStringData) fqn).getString();
                    } else if (fqn.getFQNType() == FQNType.code_page_name_ref) {
                        codePage = ((FQNCharStringData) fqn).getString();
                    }
                    break;
                case resource_local_identifier:
                    ResourceLocalId rid = (ResourceLocalId) triplet;
                    if (rid.getResourceType() == ResourceType.CODED_FONT) {
                        resourceId = rid.getResourceLocalId();
                    }
                }
            }
            if (resourceId != 0 && characterSet != null && codePage != null) {
                fontMappings.put(resourceId, new CharacterSetCodePage(characterSet, codePage));
            }
        }
    }

    public CharacterSetCodePage getFontMappings(byte resourceId) {
        return fontMappings.get(resourceId);
    }

    public static final class CharacterSetCodePage {
        private final String characterSet;
        private final String codePage;

        private CharacterSetCodePage(String characterSet, String codePage) {
            this.characterSet = characterSet;
            this.codePage = codePage;
        }

        public String getCharacterSet() {
            return characterSet;
        }

        public String getCodePage() {
            return codePage;
        }

        @Override
        public String toString() {
            return "CharacterSet=" + characterSet + " CodePage=" + codePage;
        }
    }
}
