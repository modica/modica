package org.modica.afp.ioca;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.NamedColor;

/**
 * Sets the bi-level image colour.
 */
public class SetBilevelImageColor implements SelfDefiningField {
    private static int LENGTH = 5;

    private final byte area;
    private final NamedColor colour;

    public SetBilevelImageColor(Parameters params) {
        byte length = params.getByte();
        assert length == 0x04;
        area = params.getByte();
        assert area == 0x00;
        byte reserved = params.getByte();
        assert reserved == (byte) 0x00;
        colour = NamedColor.getValue(params);
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    @Override
    public byte getId() {
        return (byte) 0xF6;
    }

    /**
     * Return the colour.
     *
     * @return the colour
     */
    public NamedColor getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return "SetBilevelImageColor area=" + area + " colour=" + colour;
    }

    @Override
    public String getName() {
        return "SetBilevelImageColor";
    }

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("Colour", colour));
        return params;
    }
}
