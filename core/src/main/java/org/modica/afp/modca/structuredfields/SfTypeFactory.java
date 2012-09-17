package org.modica.afp.modca.structuredfields;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.structuredfields.StructuredField.Builder;
import org.modica.afp.modca.structuredfields.begin.BeginActiveEnvironmentGroup.BAGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginCodePage.BCPBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginDocument.BDTBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginFont.BFNBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginImageObject.BIMBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginNamedPageGroup.BNGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginObjectEnvironmentGroup.BOGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginPage.BPGBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginPresentationTextObject.BPTBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginResource.BRSBuilder;
import org.modica.afp.modca.structuredfields.begin.BeginResourceGroup.BRGBuilder;
import org.modica.afp.modca.structuredfields.control.CodePageControl.CPCBuilder;
import org.modica.afp.modca.structuredfields.data.ImagePictureData.IPDBuilder;
import org.modica.afp.modca.structuredfields.data.NoOperation.NOPBuilder;
import org.modica.afp.modca.structuredfields.data.PresentationTextData.PTXBuilder;
import org.modica.afp.modca.structuredfields.descriptor.CodePageDescriptor.CPDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.FontDescriptor.FNDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.ImageDataDescriptor.IDDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.ObjectAreaDescriptor.OBDBuilder;
import org.modica.afp.modca.structuredfields.descriptor.PageDescriptor.PGDBuilder;
import org.modica.afp.modca.structuredfields.end.EndActiveEnvironmentGroup.EAGBuilder;
import org.modica.afp.modca.structuredfields.end.EndCodePage.ECPBuilder;
import org.modica.afp.modca.structuredfields.end.EndDocument.EDTBuilder;
import org.modica.afp.modca.structuredfields.end.EndImageObject.EIMBuilder;
import org.modica.afp.modca.structuredfields.end.EndNamedPageGroup.ENGBuilder;
import org.modica.afp.modca.structuredfields.end.EndPage.EPGBuilder;
import org.modica.afp.modca.structuredfields.end.EndPresentationTextObject.EPTBuilder;
import org.modica.afp.modca.structuredfields.end.EndResourceGroup.ERGBuilder;
import org.modica.afp.modca.structuredfields.include.IncludeObject.IOBBuilder;
import org.modica.afp.modca.structuredfields.index.CodePageIndex.CPIBuilder;
import org.modica.afp.modca.structuredfields.map.MapCodedFont.MCFBuilder;
import org.modica.afp.modca.structuredfields.map.MapImageObject.MIOBuilder;
import org.modica.afp.modca.structuredfields.migration.PresentationTextDataDescriptor.PTDBuilder;
import org.modica.afp.modca.structuredfields.position.ObjectAreaPosition.OBPBuilder;
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
        MFC(CategoryCode.medium, "Medium Finishing Control", new NotYetImplementedBuilder()),
        TLE(CategoryCode.process_element, "Tag Logical Element", new NotYetImplementedBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.Attribute;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Attribute(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum CopyCount implements SfType {
        MCC(CategoryCode.medium, "Medium Copy Count", new NotYetImplementedBuilder()),
        FNM(CategoryCode.font, "Font Patterns Map", new NotYetImplementedBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.CopyCount;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private CopyCount(CategoryCode categoryCode, String name, Builder builder) {
            this.category = categoryCode;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Descriptor implements SfType {
        OBD(CategoryCode.object_area, "Object Area Descriptor", new OBDBuilder()),
        IID(CategoryCode.im_image, "IM Image Input Descriptor (C)", new NotYetImplementedBuilder()),
        CPD(CategoryCode.code_page, "Code Page Descriptor", new CPDBuilder()),
        MDD(CategoryCode.medium, "Medium Descriptor", new NotYetImplementedBuilder()),
        FND(CategoryCode.font, "Font Descriptor", new FNDBuilder()),
        CDD(CategoryCode.object_container, "Container Data Descriptor", new NotYetImplementedBuilder()),
        PTD1(CategoryCode.presentation_text, "Presentation Text Descriptor Format-1 (C)", new PTDBuilder()),
        PGD(CategoryCode.page, "Page Descriptor", new PGDBuilder()),
        GDD(CategoryCode.graphics, "Graphics Data Descriptor", new NotYetImplementedBuilder()),
        FGD(CategoryCode.form_environment_group, "Form Environment Group Descriptor (O)", new NotYetImplementedBuilder()),
        BDD(CategoryCode.barcode, "Bar Code Data Descriptor", new NotYetImplementedBuilder()),
        IDD(CategoryCode.image, "Image Data Descriptor", new IDDBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.Descriptor;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Descriptor(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Control implements SfType {
        IOC(CategoryCode.im_image, "IM Image Output Control (C)", new NotYetImplementedBuilder()),
        CPC(CategoryCode.code_page, "Code Page Control", new CPCBuilder()),
        MMC(CategoryCode.medium, "Medium Modification Control", new NotYetImplementedBuilder()),
        FNC(CategoryCode.font, "Font Control", new NotYetImplementedBuilder()),
        CFC(CategoryCode.coded_font, "Coded Font Control", new NotYetImplementedBuilder()),
        CTC(CategoryCode.presentation_text, "Composed Text Control (O)", new NotYetImplementedBuilder()),
        PEC(CategoryCode.document, "Presentation Environment Control", new NotYetImplementedBuilder()),
        PMC(CategoryCode.page, "Page Modification Control", new NotYetImplementedBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.Control;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Control(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Begin implements SfType {
        BPS(CategoryCode.page_segment, "Page Segment", new NotYetImplementedBuilder()),
        BCA(CategoryCode.color_attribute_table, "Color Attribute Table", new NotYetImplementedBuilder()),
        BII(CategoryCode.im_image, "IM Image (C)", new NotYetImplementedBuilder()),
        BCP(CategoryCode.code_page, "Code Page", new BCPBuilder()),
        BFN(CategoryCode.font, "Font", new BFNBuilder()),
        BCF(CategoryCode.coded_font, "Coded Font", new NotYetImplementedBuilder()),
        BOC(CategoryCode.object_container, "Object Container", new NotYetImplementedBuilder()),
        BPT(CategoryCode.presentation_text, "Presentation Text Object", new BPTBuilder()),
        BDI(CategoryCode.index, "Document Index", new NotYetImplementedBuilder()),
        BDT(CategoryCode.document, "Document", new BDTBuilder()),
        BNG(CategoryCode.page_group, "Named Page Group", new BNGBuilder()),
        BPG(CategoryCode.page, "Page", new BPGBuilder()),
        BGR(CategoryCode.graphics, "Graphics Object", new NotYetImplementedBuilder()),
        BDG(CategoryCode.document_environment_group, "Document Environment Group", new NotYetImplementedBuilder()),
        BFG(CategoryCode.form_environment_group, "Form Environment Group (O)", new NotYetImplementedBuilder()),
        BRG(CategoryCode.resource_group, "Resource Group", new BRGBuilder()),
        BOG(CategoryCode.object_environment_group, "Object Environment Group", new BOGBuilder()),
        BAG(CategoryCode.active_environment_group, "Active Environment Group", new BAGBuilder()),
        BMM(CategoryCode.medium_map, "Medium Map", new NotYetImplementedBuilder()),
        BFM(CategoryCode.form_map, "Form Map", new NotYetImplementedBuilder()),
        BRS(CategoryCode.name_resource, "Resource", new BRSBuilder()),
        BSG(CategoryCode.resource_enviroment_group, "Resource Environment Group", new NotYetImplementedBuilder()),
        BMO(CategoryCode.overlay, "Overlay", new NotYetImplementedBuilder()),
        BBC(CategoryCode.barcode, "Bar Code Object", new NotYetImplementedBuilder()),
        BIM(CategoryCode.image, "Image Object", new BIMBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.Begin;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Begin(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = "Begin " + name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum End implements SfType {
        EPS(CategoryCode.page_segment, "Page Segment", new NotYetImplementedBuilder()),
        ECA(CategoryCode.color_attribute_table, "Color Attribute Table", new NotYetImplementedBuilder()),
        EII(CategoryCode.im_image, "IM Image (C)", new NotYetImplementedBuilder()),
        ECP(CategoryCode.code_page, "Code Page", new ECPBuilder()),
        EFN(CategoryCode.font, "Font", new NotYetImplementedBuilder()),
        ECF(CategoryCode.coded_font, "Coded Font", new NotYetImplementedBuilder()),
        EOC(CategoryCode.object_container, "Object Container", new NotYetImplementedBuilder()),
        EPT(CategoryCode.presentation_text, "Presentation Text Object", new EPTBuilder()),
        EDI(CategoryCode.index, "Document Index", new NotYetImplementedBuilder()),
        EDT(CategoryCode.document, "Document", new EDTBuilder()),
        ENG(CategoryCode.page_group, "Named Page Group", new ENGBuilder()),
        EPG(CategoryCode.page, "Page", new EPGBuilder()),
        EGR(CategoryCode.graphics, "Graphics Object", new NotYetImplementedBuilder()),
        EDG(CategoryCode.document_environment_group, "Document Environment Group", new NotYetImplementedBuilder()),
        EFG(CategoryCode.form_environment_group, "Form Environment Group (O)", new NotYetImplementedBuilder()),
        ERG(CategoryCode.resource_group, "Resource Group", new ERGBuilder()),
        EOG(CategoryCode.object_environment_group, "Object Environment Group", new NotYetImplementedBuilder()),
        EAG(CategoryCode.active_environment_group, "Active Environment Group", new EAGBuilder()),
        EMM(CategoryCode.medium_map, "Medium Map", new NotYetImplementedBuilder()),
        EFM(CategoryCode.form_map, "Form Map", new NotYetImplementedBuilder()),
        ERS(CategoryCode.name_resource, "Resource", new NotYetImplementedBuilder()),
        ESG(CategoryCode.resource_enviroment_group, "Resource Environment Group", new NotYetImplementedBuilder()),
        EMO(CategoryCode.overlay, "Overlay", new NotYetImplementedBuilder()),
        EBC(CategoryCode.barcode, "Bar Code Object", new NotYetImplementedBuilder()),
        EIM(CategoryCode.image, "Image Object", new EIMBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.End;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private End(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = "End " + name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Index implements SfType {
        CPI(CategoryCode.code_page, "Code Page Index", new CPIBuilder()),
        FNI(CategoryCode.font, "Font Index", new NotYetImplementedBuilder()),
        CFI(CategoryCode.coded_font, "Coded Font Index", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Index;
        private final String name;
        private final CategoryCode category;
        private final Builder builder;

        private Index(CategoryCode category, String name, Builder builder) {
            this.name = name;
            this.category = category;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Orientation implements SfType {
        FNO(CategoryCode.font, "Font Orientation", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Orientation;
        private final String name;
        private final CategoryCode category;
        private final Builder builder;

        private Orientation(CategoryCode category, String name, Builder builder) {
            this.name = name;
            this.category = category;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Map implements SfType {
        MCA(CategoryCode.color_attribute_table, "Map Color Attribute Table", new NotYetImplementedBuilder()),
        MMT(CategoryCode.medium, "Map Media Type", new NotYetImplementedBuilder()),
        FNN(CategoryCode.font, "Font Name Map", new NotYetImplementedBuilder()),
        MCF(CategoryCode.coded_font, "Map Coded Font", new MCFBuilder()),
        MCD(CategoryCode.object_container, "Map Container Data", new NotYetImplementedBuilder()),
        MPG(CategoryCode.page, "Map Page", new NotYetImplementedBuilder()),
        MGO(CategoryCode.graphics, "Map Graphics Object", new NotYetImplementedBuilder()),
        MDR(CategoryCode.data_resource, "Map Data Resource", new NotYetImplementedBuilder()),
        IMM(CategoryCode.medium_map, "Invoke Medium Map", new NotYetImplementedBuilder()),
        MPO(CategoryCode.page_overlay, "Map Page Overlay", new NotYetImplementedBuilder()),
        MSU(CategoryCode.data_supression, "Map Suppression", new NotYetImplementedBuilder()),
        MBC(CategoryCode.barcode, "Map Bar Code Object", new NotYetImplementedBuilder()),
        MIO(CategoryCode.image, "Map Image Object", new MIOBuilder());

        public static final TypeCode TYPE_CODE = TypeCode.Map;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Map(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Position implements SfType {
        OBP(CategoryCode.object_area, "Object Area Position", new OBPBuilder()),
        ICP(CategoryCode.im_image, "IM Image Cell Position (C)", new NotYetImplementedBuilder()),
        FNP(CategoryCode.font, "Font Position", new NotYetImplementedBuilder()),
        PGP1(CategoryCode.page, "Page Position Format-1 (C)", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Position;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Position(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Process implements SfType {
        PPO(CategoryCode.data_resource, "Preprocess Presentation Object", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Process;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Process(CategoryCode type, String name, Builder builder) {
            this.category = type;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Include implements SfType {
        IPS(CategoryCode.page_segment, "Page Segment", new NotYetImplementedBuilder()),
        IPG(CategoryCode.page, "Page", new NotYetImplementedBuilder()),
        IOB(CategoryCode.data_resource, "Object", new IOBBuilder()),
        IPO(CategoryCode.page_overlay, "Page Overlay", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Include;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Include(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = "Include " + name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Table implements SfType {
        CAT(CategoryCode.color_attribute_table, "Color Attribute Table", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Table;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Table(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Migration implements SfType {
        MPS(CategoryCode.page_segment, "Map Page Segment", new NotYetImplementedBuilder()),
        MCF1(CategoryCode.coded_font, "Map Coded Font Format-1 (C)", new NotYetImplementedBuilder()),
        PTD(CategoryCode.presentation_text, "Presentation Text Data Descriptor", new PTDBuilder()),
        PGP(CategoryCode.page, "Page Position", new NotYetImplementedBuilder()),
        MMO(CategoryCode.overlay, "Map Medium Overlay", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Migration;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Migration(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Variable implements SfType {
        PFC(CategoryCode.medium, "Presentation Fidelity Control", new NotYetImplementedBuilder()),
        IEL(CategoryCode.index, "Index Element", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Variable;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Variable(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Link implements SfType {
        LLE(CategoryCode.process_element, "Link Logical Element", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Link;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Link(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    public enum Data implements SfType {
        IRD(CategoryCode.im_image, "IM Image Raster Data (C)", new NotYetImplementedBuilder()),
        FNG(CategoryCode.font, "Font Patterns", new NotYetImplementedBuilder()),
        OCD(CategoryCode.object_container, "Object Container Data", new NotYetImplementedBuilder()),
        PTX(CategoryCode.presentation_text, "Presentation Text Data", new PTXBuilder()),
        GAD(CategoryCode.graphics, "Graphics Data", new NotYetImplementedBuilder()),
        BDA(CategoryCode.barcode, "Bar Code Data", new NotYetImplementedBuilder()),
        NOP(CategoryCode.no_operation, "No Operation", new NOPBuilder()),
        IPD(CategoryCode.image, "Image Picture Data", new IPDBuilder()),
        // CTX is deprecated
        CTX(CategoryCode.presentation_text, "Composed Text Data", new NotYetImplementedBuilder());

        private static final TypeCode TYPE_CODE = TypeCode.Data;
        private final CategoryCode category;
        private final String name;
        private final Builder builder;

        private Data(CategoryCode category, String name, Builder builder) {
            this.category = category;
            this.name = name;
            this.builder = builder;
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

        @Override
        public Builder getBuilder() {
            return builder;
        }
    }

    private final static class NotYetImplementedBuilder implements Builder {
        @Override
        public StructuredField create(StructuredFieldIntroducer intro, Parameters params,
                Context context) throws UnsupportedEncodingException, MalformedURLException {
            return null;
        }
    }
}
