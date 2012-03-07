package org.modica.afp.modca.structuredfields;

import java.util.HashMap;

import org.modica.common.ByteUtils;

public abstract class SfTypeFactory {
    private static final java.util.Map<Byte, java.util.Map<Byte, SfType>> SF_TYPES = new HashMap<Byte, java.util.Map<Byte, SfType>>();

    static {
        registerStructuredFieldType(
                Attribute.values(),
                CopyCount.values(),
                Descriptor.values(),
                Control.values(),
                Begin.values(),
                End.values(),
                Index.values(),
                Orientation.values(),
                Map.values(),
                Position.values(),
                Process.values(),
                Include.values(),
                Table.values(),
                Migration.values(),
                Variable.values(),
                Link.values(),
                Data.values());
    }

    private static void registerStructuredFieldType(SfType[]... sfTypes) {
        for (SfType[] sfTypeArray : sfTypes) {
            byte typeID = sfTypeArray[0].getTypeCode().getValue();

            java.util.Map<Byte, SfType> map = new HashMap<Byte, SfType>();
            for (SfType sfType : sfTypeArray) {
                map.put(sfType.getCategoryCode().getValue(), sfType);
            }
            SF_TYPES.put(typeID, map);
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
        MFC(CategoryCode.medium, "Medium Finishing Control"),
        TLE(CategoryCode.process_element, "Tag Logical Element");

        public static final TypeCode TYPE_CODE = TypeCode.Attribute;

        private final CategoryCode category;

        private final String name;

        private Attribute(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum CopyCount implements SfType {
        MCC(CategoryCode.medium, "Medium Copy Count"),
        FNM(CategoryCode.font, "Font Patterns Map");

        public static final TypeCode TYPE_CODE = TypeCode.CopyCount;

        private final CategoryCode category;

        private final String name;


        private CopyCount(CategoryCode categoryCode, String name) {
            this.category = categoryCode;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Descriptor implements SfType {
        OBD(CategoryCode.object_area, "Object Area Descriptor"),
        IID(CategoryCode.im_image, "IM Image Input Descriptor (C)"),
        CPD(CategoryCode.code_page, "Code Page Descriptor"),
        MDD(CategoryCode.medium, "Medium Descriptor"),
        FND(CategoryCode.font, "Font Descriptor"),
        CDD(CategoryCode.object_container, "Container Data Descriptor"),
        PTD1(CategoryCode.presentation_text, "Presentation Text Descriptor Format-1 (C)"),
        PGD(CategoryCode.page, "Page Descriptor"),
        GDD(CategoryCode.graphics, "Graphics Data Descriptor"),
        FGD(CategoryCode.form_environment_group, "Form Environment Group Descriptor (O)"),
        BDD(CategoryCode.barcode, "Bar Code Data Descriptor"),
        IDD(CategoryCode.image, "Image Data Descriptor");

        public static final TypeCode TYPE_CODE = TypeCode.Descriptor;

        private final CategoryCode category;

        private final String name;

        private Descriptor(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Control implements SfType {
        IOC(CategoryCode.im_image, "IM Image Output Control (C)"),
        CPC(CategoryCode.code_page, "Code Page Control"),
        MMC(CategoryCode.medium, "Medium Modification Control"),
        FNC(CategoryCode.font, "Font Control"),
        CFC(CategoryCode.coded_font, "Coded Font Control"),
        CTC(CategoryCode.presentation_text, "Composed Text Control (O)"),
        PEC(CategoryCode.document, "Presentation Environment Control"),
        PMC(CategoryCode.page, "Page Modification Control");

        public static final TypeCode TYPE_CODE = TypeCode.Control;

        private final CategoryCode category;

        private final String name;

        private Control(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Begin implements SfType {
        BPS(CategoryCode.page_segment, "Page Segment"),
        BCA(CategoryCode.color_attribute_table, "Color Attribute Table"),
        BII(CategoryCode.im_image, "IM Image (C)"),
        BCP(CategoryCode.code_page, "Code Page"),
        BFN(CategoryCode.font, "Font"),
        BCF(CategoryCode.coded_font, "Coded Font"),
        BOC(CategoryCode.object_container, "Object Container"),
        BPT(CategoryCode.presentation_text, "Presentation Text Object"),
        BDI(CategoryCode.index, "Document Index"),
        BDT(CategoryCode.document, "Document"),
        BNG(CategoryCode.page_group, "Named Page Group"),
        BPG(CategoryCode.page, "Page"),
        BGR(CategoryCode.graphics, "Graphics Object"),
        BDG(CategoryCode.document_environment_group, "Document Environment Group"),
        BFG(CategoryCode.form_environment_group, "Form Environment Group (O)"),
        BRG(CategoryCode.resource_group, "Resource Group"),
        BOG(CategoryCode.object_environment_group, "Object Environment Group"),
        BAG(CategoryCode.active_environment_group, "Active Environment Group"),
        BMM(CategoryCode.medium_map, "Medium Map"),
        BFM(CategoryCode.form_map, "Form Map"),
        BRS(CategoryCode.name_resource, "Resource"),
        BSG(CategoryCode.resource_enviroment_group, "Resource Environment Group"),
        BMO(CategoryCode.overlay, "Overlay"),
        BBC(CategoryCode.barcode, "Bar Code Object"),
        BIM(CategoryCode.image, "Image Object");

        public static final TypeCode TYPE_CODE = TypeCode.Begin;

        private final CategoryCode category;

        private final String name;

        private Begin(CategoryCode category, String name) {
            this.category = category;
            this.name = "Begin " + name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum End implements SfType {
        EPS(CategoryCode.page_segment, "Page Segment"),
        ECA(CategoryCode.color_attribute_table, "Color Attribute Table"),
        EII(CategoryCode.im_image, "IM Image (C)"),
        ECP(CategoryCode.code_page, "Code Page"),
        EFN(CategoryCode.font, "Font"),
        ECF(CategoryCode.coded_font, "Coded Font"),
        EOC(CategoryCode.object_container, "Object Container"),
        EPT(CategoryCode.presentation_text, "Presentation Text Object"),
        EDI(CategoryCode.index, "Document Index"),
        EDT(CategoryCode.document, "Document"),
        ENG(CategoryCode.page_group, "Named Page Group"),
        EPG(CategoryCode.page, "Page"),
        EGR(CategoryCode.graphics, "Graphics Object"),
        EDG(CategoryCode.document_environment_group, "Document Environment Group"),
        EFG(CategoryCode.form_environment_group, "Form Environment Group (O)"),
        ERG(CategoryCode.resource_group, "Resource Group"),
        EOG(CategoryCode.object_environment_group, "Object Environment Group"),
        EAG(CategoryCode.active_environment_group, "Active Environment Group"),
        EMM(CategoryCode.medium_map, "Medium Map"),
        EFM(CategoryCode.form_map, "Form Map"),
        ERS(CategoryCode.name_resource, "Resource"),
        ESG(CategoryCode.resource_enviroment_group, "Resource Environment Group"),
        EMO(CategoryCode.overlay, "Overlay"),
        EBC(CategoryCode.barcode, "Bar Code Object"),
        EIM(CategoryCode.image, "Image Object");

        public static final TypeCode TYPE_CODE = TypeCode.End;

        private final CategoryCode category;

        private final String name;

        private End(CategoryCode category, String name) {
            this.category = category;
            this.name = "End " + name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Index implements SfType {
        CPI(CategoryCode.code_page, "Code Page Index"),
        FNI(CategoryCode.font, "Font Index"),
        CFI(CategoryCode.coded_font, "Coded Font Index");


        private final String name;
        private final CategoryCode category;
        private static final TypeCode TYPE_CODE = TypeCode.Index;

        private Index(CategoryCode category, String name) {
            this.name = name;
            this.category = category;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Orientation implements SfType {
        FNO(CategoryCode.font, "Font Orientation");

        private final String name;
        private final CategoryCode category;
        private static final TypeCode TYPE_CODE = TypeCode.Orientation;

        private Orientation(CategoryCode category, String name) {
            this.name = name;
            this.category = category;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum Map implements SfType {
        MCA(CategoryCode.color_attribute_table, "Map Color Attribute Table"),
        MMT(CategoryCode.medium, "Map Media Type"),
        FNN(CategoryCode.font, "Font Name Map"),
        MCF(CategoryCode.coded_font, "Map Coded Font"),
        MCD(CategoryCode.object_container, "Map Container Data"),
        MPG(CategoryCode.page, "Map Page"),
        MGO(CategoryCode.graphics, "Map Graphics Object"),
        MDR(CategoryCode.data_resource, "Map Data Resource"),
        IMM(CategoryCode.medium_map, "Invoke Medium Map"),
        MPO(CategoryCode.page_overlay, "Map Page Overlay"),
        MSU(CategoryCode.data_supression, "Map Suppression"),
        MBC(CategoryCode.barcode, "Map Bar Code Object"),
        MIO(CategoryCode.image, "Map Image Object");

        public static final TypeCode TYPE_CODE = TypeCode.Map;

        private final CategoryCode category;

        private final String name;

        private Map(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Position implements SfType {
        OBP(CategoryCode.object_area, "Object Area Position"),
        ICP(CategoryCode.im_image, "IM Image Cell Position (C)"),
        FNP(CategoryCode.font, "Font Position"),
        PGP1(CategoryCode.page, "Page Position Format-1 (C)");

        private static final TypeCode TYPE_CODE = TypeCode.Position;

        private final CategoryCode category;

        private final String name;

        private Position(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Process implements SfType {
        PPO(CategoryCode.data_resource, "Preprocess Presentation Object");

        private static final TypeCode TYPE_CODE = TypeCode.Process;

        private final CategoryCode category;

        private final String name;

        private Process(CategoryCode type, String name) {
            this.category = type;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Include implements SfType {
        IPS(CategoryCode.page_segment, "Page Segment"),
        IPG(CategoryCode.page, "Page"),
        IOB(CategoryCode.data_resource, "Object"),
        IPO(CategoryCode.page_overlay, "Page Overlay");

        private static final TypeCode TYPE_CODE = TypeCode.Include;

        private final CategoryCode category;

        private final String name;

        private Include(CategoryCode category, String name) {
            this.category = category;
            this.name = "Include " + name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Table implements SfType {
        CAT(CategoryCode.color_attribute_table, "Color Attribute Table");

        private static final TypeCode TYPE_CODE = TypeCode.Table;

        private final CategoryCode category;

        private final String name;

        private Table(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Migration implements SfType {
        MPS(CategoryCode.page_segment, "Map Page Segment"),
        MCF1(CategoryCode.coded_font, "Map Coded Font Format-1 (C)"),
        PTD(CategoryCode.presentation_text, "Presentation Text Data Descriptor"),
        PGP(CategoryCode.page, "Page Position"),
        MMO(CategoryCode.overlay, "Map Medium Overlay");

        private static final TypeCode TYPE_CODE = TypeCode.Migration;

        private final CategoryCode category;

        private final String name;

        private Migration(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Variable implements SfType {
        PFC(CategoryCode.medium, "Presentation Fidelity Control"),
        IEL(CategoryCode.index, "Index Element");

        private static final TypeCode TYPE_CODE = TypeCode.Variable;

        private final CategoryCode category;

        private final String name;

        private Variable(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Link implements SfType {
        LLE(CategoryCode.process_element, "Link Logical Element");

        private static final TypeCode TYPE_CODE = TypeCode.Link;

        private final CategoryCode category;

        private final String name;

        private Link(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }

    public enum Data implements SfType {
        IRD(CategoryCode.im_image, "IM Image Raster Data (C)"),
        FNG(CategoryCode.font, "Font Patterns"),
        OCD(CategoryCode.object_container, "Object Container Data"),
        PTX(CategoryCode.presentation_text, "Presentation Text Data"),
        GAD(CategoryCode.graphics, "Graphics Data"),
        BDA(CategoryCode.barcode, "Bar Code Data"),
        NOP(CategoryCode.no_operation, "No Operation"),
        IPD(CategoryCode.image, "Image Picture Data"),
        // CTX is deprecated
        CTX(CategoryCode.presentation_text, "Composed Text Data");

        private static final TypeCode TYPE_CODE = TypeCode.Data;

        private final CategoryCode category;

        private final String name;

        private Data(CategoryCode category, String name) {
            this.category = category;
            this.name = name;
        }

        @Override
        public CategoryCode getCategoryCode() {
            return category;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public TypeCode getTypeCode() {
            return TYPE_CODE;
        }
    }
}
