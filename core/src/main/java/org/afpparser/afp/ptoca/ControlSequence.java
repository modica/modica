package org.afpparser.afp.ptoca;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ControlSequence {

    public enum ControlSequenceIdentifier {
        /**
         * Establishes the baseline and the current presentation position at a new B-axis
         * coordinate, Bcnew, which is a specified number of measurement units from the I-axis.
         * There is no change to the current I-axis coordinate, Ic.
         */
        ABSOLUTE_MOVE_BASELINE(0xD2),
        /**
         * Establishes the current presentation position on the baseline at a new I-axis coordinate,
         * Icnew, which is a specified number of measurement units from the B-axis. There is no
         * change to the current B-axis coordinate, Bc.
         */
        ABSOLUTE_MOVE_INLINE(0xC6),
        /**
         * Establishes the current presentation position on the baseline with the new I-axis
         * coordinate, Icnew, equal to the inline margin, and the new B-axis coordinate, Bcnew,
         * increased by the amount of the baseline increment from Bc. The baseline increment is
         * established by the Set Baseline Increment control sequence.
         */
        BEGIN_LINE(0xD8),
        /**
         * Marks the beginning of a field of presentation text, identified by a local identifier
         * (LID), which is not to be presented when the LID is activated in the controlling
         * environment. This control sequence does not alter the effects of other control sequences
         * within it, except that graphic characters and rules are not presented. Suppression of
         * presentation text by more than one control sequence at a time is not allowed; that is,
         * nesting of suppression control sequences is not allowed.
         */
        BEGIN_SUPPRESSION(0xF2),
        /**
         * Draws a line of specified length and specified width in the B-direction from the current
         * presentation position. The location of the current presentation position is unchanged.
         */
        DRAW_B_AXIS_RULE(0xE6),
        /**
         * Draws a line of specified length and specified width in the I-direction from the current
         * presentation position. The location of the current presentation position is unchanged.
         */
        DRAW_I_AXIS_RULE(0xE4),
        /**
         * Marks the end of a field of presentation text, identified by a LID, which is not to be
         * presented when the LID is activated by the controlling environment.
         */
        END_SUPPRESSION(0xF4),
        /**
         * Specifies a string of bytes that are to be ignored.
         */
        NO_OPERATION(0xF8),
        /**
         * Specifies a text field that is to be overstruck with a specified graphic character. The
         * overstrike function is initiated by an OVS control sequence with a non-zero bypass
         * identifier, and is terminated by an OVS control sequence with a zero-value bypass
         * identifier. The fields may not be nested or overlapped. The bypass identifier controls
         * which portions of a line are to be overstruck; this provides for bypassing white space
         * created by AMI, RMI, and space characters.
         */
        OVERSTRIKE(0x72),
        /**
         * Establishes the presentation position on the baseline at a new B-axis coordinate, Bcnew,
         * which is a specified number of measurement units from the current B-axis coordinate, Bc.
         * There is no change to the current I-axis coordinate, Ic.
         */
        RELATIVE_MOVE_BASELINE(0xD4),
        /**
         * Establishes the presentation position on the baseline at a new I-axis coordinate, Icnew,
         * which is a specified number of measurement units from the current I-axis position, Ic.
         * There is no change to the current B-axis coordinate, Bc.
         */
        RELATIVE_MOVE_INLINE(0xC8),
        /**
         * Specifies a string of characters that are to be repeated until the number of bytes in the
         * graphic characters presented is equal to a specified number of bytes. The string is not
         * checked for control sequences. When the specified number of bytes is equal to the number
         * of bytes in the characters in the data parameter, this control sequence is identical in
         * function to the Transparent Data control sequence.
         */
        REPEAT_STRING(0xEE),
        /**
         * Specifies the value of the increment to be added to the B-axis coordinate of the current
         * presentation position, Bc, when a Begin Line control sequence is processed.
         */
        SET_BASELINE_INCREMENT(0xD0),
        /**
         * Specifies a LID to be used as an index into the font map of the controlling environment
         * to determine which coded font, character rotation, and font modification parameters have
         * been selected for use in the object.
         */
        SET_CODED_FONT_LOCAL(0xF0),
        /**
         * Specifies a color value and defines the color space and encoding for that value. Supports
         * spot (highlight) colors and process colors. The specified color value is applied to
         * foreground areas of the text presentation space, that is, characters, rules, and
         * underscores.
         */
        SET_EXTENDED_TEXT_COLOR(0x80),
        /**
         * Specifies the value to be used as the new I-axis coordinate, Icnew, of the new
         * presentation position after a Begin Line control sequence is processed. The new
         * presentation position is the addressable position nearest to the B-axis at which the
         * character reference point of a graphic character may be placed.
         */
        SET_INLINE_MARGIN(0xC0),
        /**
         * Specifies the increment to be added to or subtracted from the I-axis coordinate of the
         * current presentation position, Ic. The direction parameter indicates whether to add or
         * subtract the increment. If the direction is positive, the increment is added; if
         * negative, the increment is subtracted. This control sequence may be used to compress or
         * expand words for emphasis, improved appearance, or justification.
         */
        SET_INTERCHARACTER_ADJUSTMENT(0xC2),
        /**
         * Specifies a named color value to be applied to foreground areas of the text presentation
         * space, that is, characters, rules, and underscores. The values of the foreground color
         * parameter serve as indexes into the color-value table found in Table 8 on page 90. The
         * precision parameter allows the control sequence to indicate whether the color must be
         * presented as specified, or a substitute color may be used.
         */
        SET_TEXT_COLOR(0x74),
        /**
         * Establishes the positive I-axis orientation as an angular displacement from the Xp-axis,
         * determining the I-direction. This control sequence also establishes the positive B-axis
         * orientation as an angular displacement from the Xp-axis, determining the B-direction.
         * <p>
         * The I-axis must be parallel to one of the Xp,Yp coordinate axes and the B-axis must be
         * parallel to the other. The determination of the orientation and direction of the I-axis
         * and B-axis places the origin of the I,B coordinate system at one of the corners of the
         * rectangular object space.
         * </p>
         */
        SET_TEXT_ORIENTATION(0xF6),
        /**
         * Specifies the increment to be used as the character increment for the character
         * identified as the Variable Space Character by the coded font or by the controlling
         * environment. This increment is added to the I-axis coordinate of the current presentation
         * position, Ic, when the Variable Space Character code point is processed in order to
         * establish the new presentation position. This has no effect on the B-axis coordinate
         * value.
         */
        SET_VARIABLE_SPACE_CHARACTER_INCREMENT(0xC4),
        /**
         * Specifies a temporary movement of the current baseline away from the established
         * baseline. The established baseline B-axis coordinate is maintained until a Temporary
         * Baseline Move control sequence occurs. Temporary moves are made by the amount of the
         * temporary baseline increment in one of three ways.
         * <p>Above Direction parameter = 3</p>
         * <p>Below Direction parameter = 2</p>
         * <p>Back to the established baseline Direction parameter = 1.</p>
         * The temporary baseline function is terminated by a TBM control sequence which returns the
         * temporary baseline to the same B-axis coordinate as that of the established baseline.
         */
        TEMPORARY_BASELINE_MOVE(0x78),
        /**
         * Specifies a string of characters that are to be presented, but not checked for control
         * sequences.
         */
        TRANSPARENT_DATA(0xDA),
        /**
         * Specifies a text field that is to be underscored. The underscore function is initiated by
         * an Underscore control sequence with a non-zero bypass identifier, and is terminated by a
         * USC control sequence with a bypass identifier of zero. The fields may not be nested or
         * overlapped. The bypass identifier controls which portions of a line are to be
         * underscored; this provides for bypassing white space created by AMI, RMI, and space
         * characters.
         */
        UNDERSCORE(0x76);

        private final byte id;

        private ControlSequenceIdentifier(int id) {
            this.id = (byte) id;
        }

        public byte getValue() {
            return id;
        }
    }

    private static Map<Byte, ControlSequenceIdentifier> CACHE = new HashMap<Byte, ControlSequenceIdentifier>();
    static {
        for (ControlSequenceIdentifier cs : ControlSequenceIdentifier.values()) {
            CACHE.put(cs.getValue(), cs);
        }
    }

    private final ControlSequenceIdentifier csId;
    private final boolean isChained;

    public ControlSequence(byte id) {
        byte thisId = id;
        if (id % 2 != 0) {
            thisId -= 1;
            isChained = true;
        } else {
            isChained = false;
        }
        csId = CACHE.get(thisId);
    }

    public ControlSequenceIdentifier getControlSequenceIdentifier() {
        return csId;
    }

    public boolean isChained() {
        return isChained;
    }
}
