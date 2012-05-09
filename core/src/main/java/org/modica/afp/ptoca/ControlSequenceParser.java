package org.modica.afp.ptoca;

import java.util.ArrayList;
import java.util.List;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;

/**
 * Parses a chain of control sequences.
 */
public class ControlSequenceParser {

    private static final byte CONTROL_SEQUENCE_PREFIX = 0x2B;
    private static final byte CONTROL_SEQUENCE_CLASS = (byte) 0xD3;

    /**
     * Parses a control sequence or a chain of control sequences and returns them as elements of a
     * list.
     *
     * @param sfParams the structured field parameters
     * @return a List of control sequences
     */
    public static List<ControlSequence> parse(Parameters sfParams, Context ctx) {
        int paramSize = sfParams.size();
        List<ControlSequence> controlSequences = new ArrayList<ControlSequence>();
        boolean startNewChain = sfParams.peekByte() == CONTROL_SEQUENCE_PREFIX;
        while (sfParams.getPosition() < paramSize) {
            ControlSequence cs;
            if (startNewChain) {
                cs = parseNewChain(sfParams, ctx);
            } else {
                cs = createControlSequence(sfParams, ctx);
            }
            startNewChain = !cs.isChained();
            controlSequences.add(cs);
        }

        return controlSequences;
    }

    private static ControlSequence parseNewChain(Parameters sfParams, Context ctx) {
        byte prefix = sfParams.getByte();
        byte classByte = sfParams.getByte();
        assert prefix == CONTROL_SEQUENCE_PREFIX;
        assert classByte == CONTROL_SEQUENCE_CLASS;
        return createControlSequence(sfParams, ctx);
    }

    private static ControlSequence createControlSequence(Parameters params, Context ctx) {
        int length = (int) params.getUInt(1);
        byte id = params.getByte();
        boolean isChained;
        if (id % 2 != 0) {
            id -= 1;
            isChained = true;
        } else {
            isChained = false;
        }
        ControlSequenceIdentifier csId = ControlSequenceIdentifier.getCsId(id);
        switch (csId) {
        case ABSOLUTE_MOVE_BASELINE:
            return new AbsoluteMoveBaseline(csId, length, isChained, params);
        case ABSOLUTE_MOVE_INLINE:
            return new AbsoluteMoveInline(csId, length, isChained, params);
        case BEGIN_LINE:
            return new BeginLine(csId, length, isChained);
        case BEGIN_SUPPRESSION:
            return new BeginSuppression(csId, length, isChained, params);
        case DRAW_B_AXIS_RULE:
            return new DrawBAxisRule(csId, length, isChained, params);
        case DRAW_I_AXIS_RULE:
            return new DrawIAxisRule(csId, length, isChained, params);
        case END_SUPPRESSION:
            return new EndSuppression(csId, length, isChained, params);
        case NO_OPERATION:
            return new NoOperation(csId, length, isChained, params);
        case OVERSTRIKE:
            return new Overstrike(csId, length, isChained, params);
        case RELATIVE_MOVE_BASELINE:
            return new RelativeMoveBaseline(csId, length, isChained, params);
        case RELATIVE_MOVE_INLINE:
            return new RelativeMoveInline(csId, length, isChained, params);
        case REPEAT_STRING:
            return new RepeatString(csId, length, isChained, params);
        case SET_BASELINE_INCREMENT:
            return new SetBaselineIncrement(csId, length, isChained, params);
        case SET_CODED_FONT_LOCAL:
            return new SetCodedFontLocal(csId, length, isChained, params, ctx);
        case SET_EXTENDED_TEXT_COLOR:
            return new SetExtendedTextColor(csId, length, isChained, params);
        case SET_INLINE_MARGIN:
            return new SetInlineMargin(csId, length, isChained, params);
        case SET_INTERCHARACTER_ADJUSTMENT:
            return new SetIntercharacterAdjustment(csId, length, isChained, params);
        case SET_TEXT_COLOR:
            return new SetTextColor(csId, length, isChained, params);
        case SET_TEXT_ORIENTATION:
            return new SetTextOrientation(csId, length, isChained, params);
        case SET_VARIABLE_SPACE_CHARACTER_INCREMENT:
            return new SetVariableSpaceCharacterIncrement(csId, length, isChained, params);
        case TRANSPARENT_DATA:
            return new TransparentData(csId, length, isChained, params, ctx);
        case UNDERSCORE:
            return new Underscore(csId, length, isChained, params);
        default:
            ControlSequence cs = new ControlSequence(csId, length, isChained) {
                @Override
                public String getValueAsString() {
                    return "Unhandled Control Sequence";
                }

                @Override
                public String toString() {
                    return getValueAsString();
                }
            };
            params.skip(cs.getLength() - 2);
            return cs;
        }
    }
}
