package org.modica.afp.modca.structuredfields;

import java.util.HashMap;

import org.modica.afp.modca.structuredfields.types.AttributeType;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.structuredfields.types.CopyCountType;
import org.modica.afp.modca.structuredfields.types.DataType;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.afp.modca.structuredfields.types.EndType;
import org.modica.afp.modca.structuredfields.types.IncludeType;
import org.modica.afp.modca.structuredfields.types.IndexType;
import org.modica.afp.modca.structuredfields.types.LinkType;
import org.modica.afp.modca.structuredfields.types.MapType;
import org.modica.afp.modca.structuredfields.types.MigrationType;
import org.modica.afp.modca.structuredfields.types.OrientationType;
import org.modica.afp.modca.structuredfields.types.PositionType;
import org.modica.afp.modca.structuredfields.types.ProcessType;
import org.modica.afp.modca.structuredfields.types.TableType;
import org.modica.afp.modca.structuredfields.types.VariableType;
import org.modica.common.ByteUtils;

public abstract class StructuredFieldTypeFactory {
    private static final java.util.Map<StructuredFieldKey, StructuredFieldType> SF_TYPES = new HashMap<StructuredFieldKey, StructuredFieldType>();

    static {
        registerStructuredFieldType(
                AttributeType.values(),
                CopyCountType.values(),
                DescriptorType.values(),
                ControlType.values(),
                BeginType.values(),
                EndType.values(),
                IndexType.values(),
                OrientationType.values(),
                MapType.values(),
                PositionType.values(),
                ProcessType.values(),
                IncludeType.values(),
                TableType.values(),
                MigrationType.values(),
                VariableType.values(),
                LinkType.values(),
                DataType.values());
    }

    private static void registerStructuredFieldType(StructuredFieldType[]... sfTypes) {
        for (StructuredFieldType[] sfTypeArray : sfTypes) {
            for (StructuredFieldType sfType : sfTypeArray) {
                SF_TYPES.put(makeFieldKey(sfType.getTypeCode().getValue(),
                        sfType.getCategoryCode().getValue()), sfType);
            }
        }
    }

    private static final class StructuredFieldKey {
        private final byte typeCode;
        private final byte categoryCode;

        private StructuredFieldKey(byte typeCode, byte categoryCode) {
            this.typeCode = typeCode;
            this.categoryCode = categoryCode;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof StructuredFieldKey)) {
                return false;
            }
            StructuredFieldKey key = (StructuredFieldKey) o;
            return this.typeCode == key.typeCode && this.categoryCode == key.categoryCode;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + typeCode;
            result = 31 * result + categoryCode;
            return result;
        }
    }

    private static StructuredFieldKey makeFieldKey(byte typeCode, byte categoryCode) {
        return new StructuredFieldKey(typeCode, categoryCode);
    }

    /**
     * Returns the structured field type given a type ID byte array.
     *
     * @param id the AFP object type ID
     * @return a structured field type
     */
    public static StructuredFieldType getValue(byte[] id) {
        assert (id[0] & 0xFF) == 0xd3;
        StructuredFieldType type = SF_TYPES.get(makeFieldKey(id[1], id[2]));
        if (type == null) {
            throw new IllegalArgumentException(ByteUtils.bytesToHex(id)
                    + " is not a valid structured field");
        }
        return type;
    }
}
