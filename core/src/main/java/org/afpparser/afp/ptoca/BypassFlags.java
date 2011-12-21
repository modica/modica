package org.afpparser.afp.ptoca;

/**
 * A flag that sets which control sequences to bypass.
 */
class BypassFlags {
    public enum Flags {
        /**
         * A value of B'0' in this bit indicates that the controlled white space generated as a result
         * of a Relative Move Inline control sequence is to be underscored. A value of B'1' in this bit
         * indicates that such controlled white space is not to be underscored. It should be bypassed.
         */
        RELATIVE_MOVE_INLINE,
        /**
         * A value of B'0' in this bit indicates that the controlled white space generated as a result
         * of an Absolute Move Inline control sequence is to be underscored. A value of B'1' in this bit
         * indicates that such controlled white space is not to be underscored. It should be bypassed.
         */
        ABSOLUTE_MOVE_INLINE,
        /**
         * A value of B'0' in this bit indicates that the controlled white space generated as a result
         * of space characters or variable space characters is to be underscored. A value of B'1' in
         * this bit indicates that such controlled white space is not to be underscored. It should be
         * bypassed.
         */
        SPACE_CHARACTERS,
        /**
         * A value of B'0' in this bit activates the other bypass flags. A value of B'1' in this bit
         * indicates that the other bypass flags are overridden, and that all text and white space
         * bounded by the USC pair should be underscored. If the value of the identifier is the default
         * indicator, a value is obtained from the hierarchy.
         */
        NO_BYPASS_IN_EFFECT;

        private final byte bitMask;

        private Flags() {
            bitMask = (byte) (1 << (4 - this.ordinal()));
        }

        public boolean isSet(byte flags) {
            return (bitMask & flags) != 0;
        }
    }

    private final boolean bypassRelativeMoveInline;
    private final boolean bypassAbsoluteMoveInline;
    private final boolean bypassSpaceChars;

    BypassFlags(byte flags) {
        if (Flags.NO_BYPASS_IN_EFFECT.isSet(flags)) {
            bypassRelativeMoveInline = Flags.RELATIVE_MOVE_INLINE.isSet(flags);
            bypassAbsoluteMoveInline = Flags.ABSOLUTE_MOVE_INLINE.isSet(flags);
            bypassSpaceChars = Flags.SPACE_CHARACTERS.isSet(flags);
        } else {
            bypassRelativeMoveInline = bypassAbsoluteMoveInline = bypassSpaceChars = false;
        }
    }

    boolean bypassRelativeMoveInline() {
        return bypassRelativeMoveInline;
    }

    boolean bypassAbsoluteMoveInline() {
        return bypassAbsoluteMoveInline;
    }

    boolean bypassSpaceChars() {
        return bypassSpaceChars;
    }
}