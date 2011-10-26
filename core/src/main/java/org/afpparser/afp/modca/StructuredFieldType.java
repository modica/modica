package org.afpparser.afp.modca;

import java.util.Arrays;

public enum StructuredFieldType {
    MFC("D3A088", "Medium Finishing Control"),
    TLE("D3A090", "Tag Logical Element"),
    MCC("D3A288", "Medium Copy Count"),
    OBD("D3A66B", "Object Area Descriptor"),
    IID("D3A67B", "IM Image Input Descriptor (C)"),
    MDD("D3A688", "Medium Descriptor"),
    CDD("D3A692", "Container Data Descriptor"),
    PTD1("D3A69B", "Presentation Text Descriptor Format-1 (C)"),
    PGD("D3A6AF", "Page Descriptor"),
    GDD("D3A6BB", "Graphics Data Descriptor"),
    FGD("D3A6C5", "Form Environment Group Descriptor (O)"),
    BDD("D3A6EB", "Bar Code Data Descriptor"),
    IDD("D3A6FB", "Image Data Descriptor"),
    IOC("D3A77B", "IM Image Output Control (C)"),
    MMC("D3A788", "Medium Modification Control"),
    CTC("D3A79B", "Composed Text Control (O)"),
    PEC("D3A7A8", "Presentation Environment Control"),
    PMC("D3A7AF", "Page Modification Control"),
    BPS("D3A85F", "Begin Page Segment"),
    BCA("D3A877", "Begin Color Attribute Table"),
    BII("D3A87B", "Begin IM Image (C)"),
    BOC("D3A892", "Begin Object Container"),
    BPT("D3A89B", "Begin Presentation Text Object"),
    BDI("D3A8A7", "Begin Document Index"),
    BDT("D3A8A8", "Begin Document"),
    BNG("D3A8AD", "Begin Named Page Group"),
    BPG("D3A8AF", "Begin Page"),
    BGR("D3A8BB", "Begin Graphics Object"),
    BDG("D3A8C4", "Begin Document Environment Group"),
    BFG("D3A8C5", "Begin Form Environment Group (O)"),
    BRG("D3A8C6", "Begin Resource Group"),
    BOG("D3A8C7", "Begin Object Environment Group"),
    BAG("D3A8C9", "Begin Active Environment Group"),
    BMM("D3A8CC", "Begin Medium Map"),
    BFM("D3A8CD", "Begin Form Map"),
    BRS("D3A8CE", "Begin Resource"),
    BSG("D3A8D9", "Begin Resource Environment Group"),
    BMO("D3A8DF", "Begin Overlay"),
    BBC("D3A8EB", "Begin Bar Code Object"),
    BIM("D3A8FB", "Begin Image Object"),
    EPS("D3A95F", "End Page Segment"),
    ECA("D3A977", "End Color Attribute Table"),
    EII("D3A97B", "End IM Image (C)"),
    EOC("D3A992", "End Object Container"),
    EPT("D3A99B", "End Presentation Text Object"),
    EDI("D3A9A7", "End Document Index"),
    EDT("D3A9A8", "End Document"),
    ENG("D3A9AD", "End Named Page Group"),
    EPG("D3A9AF", "End Page"),
    EGR("D3A9BB", "End Graphics Object"),
    EDG("D3A9C4", "End Document Environment Group"),
    EFG("D3A9C5", "End Form Environment Group (O)"),
    ERG("D3A9C6", "End Resource Group"),
    EOG("D3A9C7", "End Object Environment Group"),
    EAG("D3A9C9", "End Active Environment Group"),
    EMM("D3A9CC", "End Medium Map"),
    EFM("D3A9CD", "End Form Map"),
    ERS("D3A9CE", "End Resource"),
    ESG("D3A9D9", "End Resource Environment Group"),
    EMO("D3A9DF", "End Overlay"),
    EBC("D3A9EB", "End Bar Code Object"),
    EIM("D3A9FB", "End Image Object"),
    MCA("D3AB77", "Map Color Attribute Table"),
    MMT("D3AB88", "Map Media Type"),
    MCF("D3AB8A", "Map Coded Font"),
    MCD("D3AB92", "Map Container Data"),
    MPG("D3ABAF", "Map Page"),
    MGO("D3ABBB", "Map Graphics Object"),
    MDR("D3ABC3", "Map Data Resource"),
    IMM("D3ABCC", "Invoke Medium Map"),
    MPO("D3ABD8", "Map Page Overlay"),
    MSU("D3ABEA", "Map Suppression"),
    MBC("D3ABEB", "Map Bar Code Object"),
    MIO("D3ABFB", "Map Image Object"),
    OBP("D3AC6B", "Object Area Position"),
    ICP("D3AC7B", "IM Image Cell Position (C)"),
    PGP1("D3ACAF", "Page Position Format-1 (C)"),
    PPO("D3ADC3", "Preprocess Presentation Object"),
    IPS("D3AF5F", "Include Page Segment"),
    IPG("D3AFAF", "Include Page"),
    IOB("D3AFC3", "Include Object"),
    IPO("D3AFD8", "Include Page Overlay"),
    CAT("D3B077", "Color Attribute Table"),
    MPS("D3B15F", "Map Page Segment"),
    MCF1("D3B18A", "Map Coded Font Format-1 (C)"),
    PTD("D3B19B", "Presentation Text Data Descriptor"),
    PGP("D3B1AF", "Page Position"),
    MMO("D3B1DF", "Map Medium Overlay"),
    PFC("D3B288", "Presentation Fidelity Control"),
    IEL("D3B2A7", "Index Element"),
    LLE("D3B490", "Link Logical Element"),
    IRD("D3EE7B", "IM Image Raster Data (C)"),
    OCD("D3EE92", "Object Container Data"),
    PTX("D3EE9B", "Presentation Text Data"),
    GAD("D3EEBB", "Graphics Data"),
    BDA("D3EEEB", "Bar Code Data"),
    NOP("D3EEEE", "No Operation"),
    IPD("D3EEFB", "Image Picture Data"),
    // Obsolete Structured Fields which may or may not exist.
    CTX("D3EE9B", "Composed Text Data"),
    CTD("D3A69B", "Composed Text Descriptor"),
    BCT("D3A89B", "Begin Composed Text"),
    ECT("D3A99B", "End Composed Text"),
    // AFP Triplets FOCA
    CPI("D38C87", "Code Page Index"),
    FNI("D38C89", "Font Index"),
    CFI("D38C8A", "Coded Font Index"),
    FNM("D3A289", "Font Patterns Map"),
    CPD("D3A687", "Code Page Descriptor"),
    FND("D3A689", "Font Descriptor"),
    CPC("D3A787", "Code Page Control"),
    FNC("D3A789", "Font Control"),
    CFC("D3A78A", "Coded Font Control"),
    BCP("D3A887", "Begin Code Page"),
    BFN("D3A889", "Begin Font"),
    BCF("D3A88A", "Begin Coded Font"),
    ECP("D3A987", "End Code Page"),
    EFN("D3A989", "End Font"),
    ECF("D3A98A", "End Coded Font"),
    FNN("D3AB89", "Font Name Map"),
    FNP("D3AC89", "Font Position"),
    FNO("D3AE89", "Font Orientation"),
    FNG("D3EE89", "Font Patterns");

    private final byte[] id;
    private final String fullName;

    private StructuredFieldType(String id, String name) {
        this.id = hexToBytes(id);
        this.fullName = name;
    }

    public static byte[] hexToBytes(String hex) {
        byte[] data = new byte[hex.length() / 2];
        char[] hexArray = hex.toCharArray();
        for (int i = 0; i < hexArray.length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexArray[i], 16) << 4)
                    + (Character.digit(hexArray[i + 1], 16)));
        }
        return data;
    }

    public String getFullName() {
        return fullName;
    }

    public byte[] getId() {
        return id;
    }

    public static StructuredFieldType getValue(String name) {
        for (StructuredFieldType type : StructuredFieldType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException(name + " is not a valid structured field");
    }

    public static StructuredFieldType getValue(byte[] id) {
        for (StructuredFieldType type : StructuredFieldType.values()) {
            if (Arrays.equals(type.getId(), id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Not a valid structured field");
    }

}
