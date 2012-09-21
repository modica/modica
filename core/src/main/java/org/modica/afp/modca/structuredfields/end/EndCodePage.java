package org.modica.afp.modca.structuredfields.end;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.EbcdicStringHandler;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

/**
 * The End Code Page (ECP) structured field ends the code page object.
 */
public class EndCodePage extends AbstractStructuredField {

    private final EndFieldName cpName;

    EndCodePage(StructuredFieldIntroducer introducer, Parameters params, Context ctx)
            throws UnsupportedEncodingException {
        super(introducer);
        cpName = new EndFieldName(params, EbcdicStringHandler.DEFAULT_CPGID);
        ctx.endCodePage();
    }

    /**
     * The ECP structured field name, if supplied, must match the corresponding BCP structured field
     * name. In an ECP structured field, no name or a null name (any name with X'FFFF' in the first
     * two bytes) matches any name in the BCP structured field.
     *
     * @return the code page name
     */
    public String getCodePageName() {
        return cpName.getName();
    }

    /**
     * If the name begins with X'FFFF' this matches any name on the BeginCodePage field.
     *
     * @return if the name matches any
     */
    public boolean nameMatchesAny() {
        return cpName.matchesAny();
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("CodePageName", getCodePageName()));
        return params;
    }

    public static final class ECPBuilder implements Builder {
        @Override
        public EndCodePage build(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException {
            return new EndCodePage(intro, params, context);
        }
    }
}
