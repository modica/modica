package org.modica.afp.modca.structuredfields;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modica.afp.modca.structuredfields.StructuredFieldType;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.End;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Map;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Position;
import org.modica.afp.modca.structuredfields.StructuredFieldTypeFactory.Process;
import org.modica.common.ByteUtils;

/**
 * A test case for {@link StructuredFieldTypeFactory}.
 */
public class SfTypeFactoryTestCase {
    @Test
    public void testAllStructuredFieldTypes() {
        // Attributes
        testStructuredFieldTypeIsCorrect(AttributeType.MFC, "D3A088");
        testStructuredFieldTypeIsCorrect(AttributeType.TLE, "D3A090");

        // Copy Counts
        testStructuredFieldTypeIsCorrect(CopyCountType.MCC, "D3A288");
        testStructuredFieldTypeIsCorrect(CopyCountType.FNM, "D3A289");

        // Descriptors
        testStructuredFieldTypeIsCorrect(DescriptorType.OBD, "D3A66B");
        testStructuredFieldTypeIsCorrect(DescriptorType.IID, "D3A67B");
        testStructuredFieldTypeIsCorrect(DescriptorType.CPD, "D3A687");
        testStructuredFieldTypeIsCorrect(DescriptorType.MDD, "D3A688");
        testStructuredFieldTypeIsCorrect(DescriptorType.CDD, "D3A692");
        testStructuredFieldTypeIsCorrect(DescriptorType.PTD1, "D3A69B");
        testStructuredFieldTypeIsCorrect(DescriptorType.PGD, "D3A6AF");
        testStructuredFieldTypeIsCorrect(DescriptorType.GDD, "D3A6BB");
        testStructuredFieldTypeIsCorrect(DescriptorType.FGD, "D3A6C5");
        testStructuredFieldTypeIsCorrect(DescriptorType.BDD, "D3A6EB");
        testStructuredFieldTypeIsCorrect(DescriptorType.IDD, "D3A6FB");

        // Control
        testStructuredFieldTypeIsCorrect(ControlType.IOC, "D3A77B");
        testStructuredFieldTypeIsCorrect(ControlType.CPC, "D3A787");
        testStructuredFieldTypeIsCorrect(ControlType.MMC, "D3A788");
        testStructuredFieldTypeIsCorrect(ControlType.FNC, "D3A789");
        testStructuredFieldTypeIsCorrect(ControlType.CFC, "D3A78A");
        testStructuredFieldTypeIsCorrect(ControlType.CTC, "D3A79B");
        testStructuredFieldTypeIsCorrect(ControlType.PEC, "D3A7A8");
        testStructuredFieldTypeIsCorrect(ControlType.PMC, "D3A7AF");

        // Begin BPS("D3A85F", "Begin Page Segment"),
        testStructuredFieldTypeIsCorrect(BeginType.BPS, "D3A85F");
        testStructuredFieldTypeIsCorrect(BeginType.BCA, "D3A877");
        testStructuredFieldTypeIsCorrect(BeginType.BII, "D3A87B");
        testStructuredFieldTypeIsCorrect(BeginType.BCP, "D3A887");
        testStructuredFieldTypeIsCorrect(BeginType.BFN, "D3A889");
        testStructuredFieldTypeIsCorrect(BeginType.BCF, "D3A88A");
        testStructuredFieldTypeIsCorrect(BeginType.BOC, "D3A892");
        testStructuredFieldTypeIsCorrect(BeginType.BPT, "D3A89B");
        testStructuredFieldTypeIsCorrect(BeginType.BDI, "D3A8A7");
        testStructuredFieldTypeIsCorrect(BeginType.BDT, "D3A8A8");
        testStructuredFieldTypeIsCorrect(BeginType.BNG, "D3A8AD");
        testStructuredFieldTypeIsCorrect(BeginType.BPG, "D3A8AF");
        testStructuredFieldTypeIsCorrect(BeginType.BGR, "D3A8BB");
        testStructuredFieldTypeIsCorrect(BeginType.BDG, "D3A8C4");
        testStructuredFieldTypeIsCorrect(BeginType.BFG, "D3A8C5");
        testStructuredFieldTypeIsCorrect(BeginType.BRG, "D3A8C6");
        testStructuredFieldTypeIsCorrect(BeginType.BOG, "D3A8C7");
        testStructuredFieldTypeIsCorrect(BeginType.BAG, "D3A8C9");
        testStructuredFieldTypeIsCorrect(BeginType.BMM, "D3A8CC");
        testStructuredFieldTypeIsCorrect(BeginType.BFM, "D3A8CD");
        testStructuredFieldTypeIsCorrect(BeginType.BRS, "D3A8CE");
        testStructuredFieldTypeIsCorrect(BeginType.BSG, "D3A8D9");
        testStructuredFieldTypeIsCorrect(BeginType.BMO, "D3A8DF");
        testStructuredFieldTypeIsCorrect(BeginType.BBC, "D3A8EB");
        testStructuredFieldTypeIsCorrect(BeginType.BIM, "D3A8FB");

        // End
        testStructuredFieldTypeIsCorrect(End.EPS, "D3A95F");
        testStructuredFieldTypeIsCorrect(End.ECA, "D3A977");
        testStructuredFieldTypeIsCorrect(End.EII, "D3A97B");
        testStructuredFieldTypeIsCorrect(End.ECP, "D3A987");
        testStructuredFieldTypeIsCorrect(End.EFN, "D3A989");
        testStructuredFieldTypeIsCorrect(End.ECF, "D3A98A");
        testStructuredFieldTypeIsCorrect(End.EOC, "D3A992");
        testStructuredFieldTypeIsCorrect(End.EPT, "D3A99B");
        testStructuredFieldTypeIsCorrect(End.EDI, "D3A9A7");
        testStructuredFieldTypeIsCorrect(End.EDT, "D3A9A8");
        testStructuredFieldTypeIsCorrect(End.ENG, "D3A9AD");
        testStructuredFieldTypeIsCorrect(End.EPG, "D3A9AF");
        testStructuredFieldTypeIsCorrect(End.EGR, "D3A9BB");
        testStructuredFieldTypeIsCorrect(End.EDG, "D3A9C4");
        testStructuredFieldTypeIsCorrect(End.EFG, "D3A9C5");
        testStructuredFieldTypeIsCorrect(End.ERG, "D3A9C6");
        testStructuredFieldTypeIsCorrect(End.EOG, "D3A9C7");
        testStructuredFieldTypeIsCorrect(End.EAG, "D3A9C9");
        testStructuredFieldTypeIsCorrect(End.EMM, "D3A9CC");
        testStructuredFieldTypeIsCorrect(End.EFM, "D3A9CD");
        testStructuredFieldTypeIsCorrect(End.ERS, "D3A9CE");
        testStructuredFieldTypeIsCorrect(End.ESG, "D3A9D9");
        testStructuredFieldTypeIsCorrect(End.EMO, "D3A9DF");
        testStructuredFieldTypeIsCorrect(End.EBC, "D3A9EB");
        testStructuredFieldTypeIsCorrect(End.EIM, "D3A9FB");

        // Map
        testStructuredFieldTypeIsCorrect(Map.MCA, "D3AB77");
        testStructuredFieldTypeIsCorrect(Map.MMT, "D3AB88");
        testStructuredFieldTypeIsCorrect(Map.MCF, "D3AB8A");
        testStructuredFieldTypeIsCorrect(Map.MCD, "D3AB92");
        testStructuredFieldTypeIsCorrect(Map.MPG, "D3ABAF");
        testStructuredFieldTypeIsCorrect(Map.MGO, "D3ABBB");
        testStructuredFieldTypeIsCorrect(Map.MDR, "D3ABC3");
        testStructuredFieldTypeIsCorrect(Map.IMM, "D3ABCC");
        testStructuredFieldTypeIsCorrect(Map.MPO, "D3ABD8");
        testStructuredFieldTypeIsCorrect(Map.MSU, "D3ABEA");
        testStructuredFieldTypeIsCorrect(Map.MBC, "D3ABEB");
        testStructuredFieldTypeIsCorrect(Map.MIO, "D3ABFB");
        testStructuredFieldTypeIsCorrect(Map.FNN, "D3AB89");

        // Position
        testStructuredFieldTypeIsCorrect(Position.OBP, "D3AC6B");
        testStructuredFieldTypeIsCorrect(Position.ICP, "D3AC7B");
        testStructuredFieldTypeIsCorrect(Position.PGP1, "D3ACAF");
        testStructuredFieldTypeIsCorrect(Position.FNP, "D3AC89");

        // Process
        testStructuredFieldTypeIsCorrect(Process.PPO, "D3ADC3");
    }

    private void testStructuredFieldTypeIsCorrect(StructuredFieldType sfType, String id) {
        StructuredFieldType type = StructuredFieldTypeFactory.getValue(ByteUtils.hexToBytes(id));
        assertEquals(sfType, type);
    }
}
