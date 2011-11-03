package org.afpparser.afp.modca;

import java.util.HashMap;

import org.afpparser.common.ByteUtils;

public abstract class SfTypeFactory {
    private static final java.util.Map<Byte, java.util.Map<Byte, SfType>> SF_TYPES = new HashMap<Byte, java.util.Map<Byte, SfType>>();

    static {
        registerHomogenousSfTypes(Attribute.values(),
                CopyCount.values(),
                Descriptor.values(),
                Control.values(),
                Begin.values(),
                End.values(),
                Map.values(),
                Position.values(),
                Process.values(),
                Include.values(),
                Table.values(),
                Migration.values(),
                Variable.values(),
                Link.values(),
                Data.values());
        registerNonHomogenousSfTypes(Foca.values());
    }

    private static void registerHomogenousSfTypes(SfType[]... sfTypes) {
        for (SfType[] sfTypeArray : sfTypes) {
            byte typeID = sfTypeArray[0].getId()[1];

            java.util.Map<Byte, SfType> map = new HashMap<Byte, SfType>();
            for (SfType sfType : sfTypeArray) {
                map.put(sfType.getId()[2], sfType);
            }
            SF_TYPES.put(typeID, map);
        }
    }

    private static void registerNonHomogenousSfTypes(SfType[]... sfTypes) {
        for (SfType[] sfTypeArray : sfTypes) {
            for (SfType sfType : sfTypeArray) {
                byte[] id = sfType.getId();
                java.util.Map<Byte, SfType> map = SF_TYPES.get(id[1]);
                if (map == null) {
                    map = new HashMap<Byte, SfType>();
                }
                map.put(id[2], sfType);
                SF_TYPES.put(id[1], map);
            }
        }
    }

    /**
     * Returns the structured field type given a type ID byte array.
     *
     * @param id the AFP object type ID
     * @return a structured field type
     */
    public static SfType getValue(byte[] id) {
        java.util.Map<Byte, SfType> typeMap = SF_TYPES.get(id[1]);
        if (typeMap == null) {
            throw new IllegalArgumentException(
                    ByteUtils.bytesToHex(id) + " is not a valid structured field");
        }
        SfType type = typeMap.get(id[2]);
        if (type == null) {
            throw new IllegalArgumentException(
                    ByteUtils.bytesToHex(id) + " is not a valid structured field");
        }
        return type;
    }

    public enum Attribute implements SfType {
        MFC(0x88, "Medium Finishing Control"),
        TLE(0x90, "Tag Logical Element");

        public static final TypeCodes TYPE_CODE = TypeCodes.Attribute;

        private final int type;

        private final String name;

        private Attribute(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum CopyCount implements SfType {
        MCC(0x88, "Medium Copy Count"),
        FNM(0x89, "Font Patterns Map");

        public static final TypeCodes TYPE_CODE = TypeCodes.CopyCount;

        private final int type;

        private final String name;

        private CopyCount(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Descriptor implements SfType {
        OBD(0x6B, "Object Area Descriptor"),
        IID(0x7B, "IM Image Input Descriptor (C)"),
        CPD(0x87, "Code Page Descriptor"),
        MDD(0x88, "Medium Descriptor"),
        FND(0x89, "Font Descriptor"),
        CDD(0x92, "Container Data Descriptor"),
        PTD1(0x9B, "Presentation Text Descriptor Format-1 (C)"),
        PGD(0xAF, "Page Descriptor"),
        GDD(0xBB, "Graphics Data Descriptor"),
        FGD(0xC5, "Form Environment Group Descriptor (O)"),
        BDD(0xEB, "Bar Code Data Descriptor"),
        IDD(0xFB, "Image Data Descriptor");

        public static final TypeCodes TYPE_CODE = TypeCodes.Descriptor;

        private final int type;

        private final String name;

        private Descriptor(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Control implements SfType {
        IOC(0x7B, "IM Image Output Control (C)"),
        CPC(0x87, "Code Page Control"),
        MMC(0x88, "Medium Modification Control"),
        FNC(0x89, "Font Control"),
        CFC(0x8A, "Coded Font Control"),
        CTC(0x9B, "Composed Text Control (O)"),
        PEC(0xA8, "Presentation Environment Control"),
        PMC(0xAF, "Page Modification Control");

        public static final TypeCodes TYPE_CODE = TypeCodes.Control;

        private final int type;

        private final String name;

        private Control(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Begin implements SfType {
        BPS(0x5F, "Page Segment"),
        BCA(0x77, "Color Attribute Table"),
        BII(0x7B, "IM Image (C)"),
        BCP(0x87, "Code Page"),
        BFN(0x89, "Font"),
        BCF(0x8A, "Coded Font"),
        BOC(0x92, "Object Container"),
        BPT(0x9B, "Presentation Text Object"),
        BDI(0xA7, "Document Index"),
        BDT(0xA8, "Document"),
        BNG(0xAD, "Named Page Group"),
        BPG(0xAF, "Page"),
        BGR(0xBB, "Graphics Object"),
        BDG(0xC4, "Document Environment Group"),
        BFG(0xC5, "Form Environment Group (O)"),
        BRG(0xC6, "Resource Group"),
        BOG(0xC7, "Object Environment Group"),
        BAG(0xC9, "Active Environment Group"),
        BMM(0xCC, "Medium Map"),
        BFM(0xCD, "Form Map"),
        BRS(0xCE, "Resource"),
        BSG(0xD9, "Resource Environment Group"),
        BMO(0xDF, "Overlay"),
        BBC(0xEB, "Bar Code Object"),
        BIM(0xFB, "Image Object");

        public static final TypeCodes TYPE_CODE = TypeCodes.Begin;

        private final int type;

        private final String name;

        private Begin(int type, String name) {
            this.type = type;
            this.name = "Begin " + name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum End implements SfType {
        EPS(0x5F, "Page Segment"),
        ECA(0x77, "Color Attribute Table"),
        EII(0x7B, "IM Image (C)"),
        ECP(0x87, "Code Page"),
        EFN(0x89, "Font"),
        ECF(0x8A, "Coded Font"),
        EOC(0x92, "Object Container"),
        EPT(0x9B, "Presentation Text Object"),
        EDI(0xA7, "Document Index"),
        EDT(0xA8, "Document"),
        ENG(0xAD, "Named Page Group"),
        EPG(0xAF, "Page"),
        EGR(0xBB, "Graphics Object"),
        EDG(0xC4, "Document Environment Group"),
        EFG(0xC5, "Form Environment Group (O)"),
        ERG(0xC6, "Resource Group"),
        EOG(0xC7, "Object Environment Group"),
        EAG(0xC9, "Active Environment Group"),
        EMM(0xCC, "Medium Map"),
        EFM(0xCD, "Form Map"),
        ERS(0xCE, "Resource"),
        ESG(0xD9, "Resource Environment Group"),
        EMO(0xDF, "Overlay"),
        EBC(0xEB, "Bar Code Object"),
        EIM(0xFB, "Image Object");

        public static final TypeCodes TYPE_CODE = TypeCodes.End;

        private final int type;

        private final String name;

        private End(int type, String name) {
            this.type = type;
            this.name = "End " + name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Map implements SfType {
        MCA(0x77, "Map Color Attribute Table"),
        MMT(0x88, "Map Media Type"),
        FNN(0x89, "Font Name Map"),
        MCF(0x8A, "Map Coded Font"),
        MCD(0x92, "Map Container Data"),
        MPG(0xAF, "Map Page"),
        MGO(0xBB, "Map Graphics Object"),
        MDR(0xC3, "Map Data Resource"),
        IMM(0xCC, "Invoke Medium Map"),
        MPO(0xD8, "Map Page Overlay"),
        MSU(0xEA, "Map Suppression"),
        MBC(0xEB, "Map Bar Code Object"),
        MIO(0xFB, "Map Image Object");

        public static final TypeCodes TYPE_CODE = TypeCodes.Map;

        private final int type;

        private final String name;

        private Map(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Position implements SfType {
        OBP(0x6B, "Object Area Position"),
        ICP(0x7B, "IM Image Cell Position (C)"),
        FNP(0x89, "Font Position"),
        PGP1(0xAF, "Page Position Format-1 (C)");

        private static final TypeCodes TYPE_CODE = TypeCodes.Position;

        private final int type;

        private final String name;

        private Position(int type, String name) {
            this.type= type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Process implements SfType {
        PPO(0xC3, "Preprocess Presentation Object");

        private static final TypeCodes TYPE_CODE = TypeCodes.Process;

        private final int type;

        private final String name;

        private Process(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Include implements SfType {
        IPS(0x5F, "Page Segment"),
        IPG(0xAF, "Page"),
        IOB(0xC3, "Object"),
        IPO(0xD8, "Page Overlay");

        private static final TypeCodes TYPE_CODE = TypeCodes.Include;

        private final int type;

        private final String name;

        private Include(int type, String name) {
            this.type = type;
            this.name = "Include " + name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Table implements SfType {
        CAT(0x77, "Color Attribute Table");

        private static final TypeCodes TYPE_CODE = TypeCodes.Table;

        private final int type;

        private final String name;

        private Table(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Migration implements SfType {
        MPS(0x5F, "Map Page Segment"),
        MCF1(0x8A, "Map Coded Font Format-1 (C)"),
        PTD(0x9B, "Presentation Text Data Descriptor"),
        PGP(0xAF, "Page Position"),
        MMO(0xDF, "Map Medium Overlay");

        private static final TypeCodes TYPE_CODE = TypeCodes.Migration;

        private final int type;

        private final String name;

        private Migration(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Variable implements SfType {
        PFC(0x88, "Presentation Fidelity Control"),
        IEL(0xA7, "Index Element");

        private static final TypeCodes TYPE_CODE = TypeCodes.Variable;

        private final int type;

        private final String name;

        private Variable(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Link implements SfType {
        LLE(0x90, "Link Logical Element");

        private static final TypeCodes TYPE_CODE = TypeCodes.Link;

        private final int type;

        private final String name;

        private Link(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Data implements SfType {
        IRD(0x7B, "IM Image Raster Data (C)"),
        FNG(0x89, "Font Patterns"),
        OCD(0x92, "Object Container Data"),
        PTX(0x9B, "Presentation Text Data"),
        GAD(0xBB, "Graphics Data"),
        BDA(0xEB, "Bar Code Data"),
        NOP(0xEE, "No Operation"),
        IPD(0xFB, "Image Picture Data"),
        CTX(0x9B, "Composed Text Data");

        private static final TypeCodes TYPE_CODE = TypeCodes.Data;

        private final int type;

        private final String name;

        private Data(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public byte[] getId() {
            return TYPE_CODE.getIdForType(type);
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Foca implements SfType {
        CPI(ByteUtils.hexToBytes("D38C87"), "Code Page Index"),
        FNI(ByteUtils.hexToBytes("D38C89"), "Font Index"),
        CFI(ByteUtils.hexToBytes("D38C8A"), "Coded Font Index"),
        FNO(ByteUtils.hexToBytes("D3AE89"), "Font Orientation");

        private final byte[] id;
        private final String name;

        private Foca(byte[] id, String name) {
            this.id = id;
            this.name = name;
        }

        public byte[] getId() {
            byte[] idCopy = new byte[3];
            System.arraycopy(id, 0, idCopy, 0, 3);
            return idCopy;
        }

        public String getName() {
            return name;
        }

        public TypeCodes getTypeCode() {
            return TypeCodes.Unknown;
        }
    }
}
