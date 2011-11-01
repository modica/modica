package org.afpparser.afp.modca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.afpparser.afp.modca.SfTypeFactory.Attribute;
import org.afpparser.afp.modca.SfTypeFactory.Begin;
import org.afpparser.afp.modca.SfTypeFactory.Control;
import org.afpparser.afp.modca.SfTypeFactory.CopyCount;
import org.afpparser.afp.modca.SfTypeFactory.Descriptor;
import org.afpparser.afp.modca.SfTypeFactory.End;
import org.afpparser.afp.modca.SfTypeFactory.Map;
import org.afpparser.afp.modca.SfTypeFactory.Position;
import org.afpparser.afp.modca.SfTypeFactory.Process;
import org.afpparser.common.ByteUtils;
import org.junit.Before;
import org.junit.Test;

public class SfIntroducerTestCase {
    private SfIntroducer sut;
    private SfType sutType;

    @Before
    public void setUp() {
        byte[] typeId = ByteUtils.createByteArray(0xD3, 0xAE, 0x89);
        sutType = SfTypeFactory.getValue(typeId);
        sut = new SfIntroducer(1L, 2, typeId, (byte) 3, 5);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, sut.getOffset());
        assertEquals(2, sut.getLength());
        assertEquals(sutType, sut.getType());
        assertTrue(sut.hasExtData());
        assertEquals(5, sut.getExtLength());
    }

    @Test
    public void testHashCodeEquals() {
        // Consistency
        for (int i = 0; i < 100; i++) {
            SfIntroducer x = createField();
            SfIntroducer y = createField();
            SfIntroducer z = createField();

            // Reflectivity
            assertTrue(x.equals(x));
            assertTrue(y.equals(y));
            assertTrue(z.equals(z));

            // Symmetry
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));

            // Transitivity
            assertTrue(x.equals(y));
            assertTrue(y.equals(z));
            assertTrue(x.equals(z));

            // hashCode
            assertEquals(x.hashCode(), y.hashCode());
            assertEquals(y.hashCode(), z.hashCode());
        }
    }

    @Test
    public void testEqualsHashCodeFailureCases() {
        SfIntroducer subject = createField();
        SfIntroducer offsetNotEqual = new SfIntroducer(2L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, offsetNotEqual);

        SfIntroducer lengthNotEquals = new SfIntroducer(1L, 1, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 5);
        checkNotEquals(subject, lengthNotEquals);

        SfIntroducer typeNotEquals = new SfIntroducer(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x90), (byte) 3, 5);
        checkNotEquals(subject, typeNotEquals);

        SfIntroducer flagsNotEquals = new SfIntroducer(1L, 2, ByteUtils.createByteArray(0xD3,
                0xA0, 0x88), (byte) 4, 5);
        checkNotEquals(subject, flagsNotEquals);

        SfIntroducer extLengthNotEquals = new SfIntroducer(1L, 2, ByteUtils.createByteArray(
                0xD3, 0xA0, 0x88), (byte) 3, 6);
        checkNotEquals(subject, extLengthNotEquals);
    }

    private void checkNotEquals(SfIntroducer o1, SfIntroducer o2) {
        assertFalse(o1.equals(o2));
        assertNotSame(o1.hashCode(), o2.hashCode());
    }

    private SfIntroducer createField() {
        return new SfIntroducer(1L, 2, ByteUtils.createByteArray(0xD3, 0xA0, 0x88), (byte) 3, 5);
    }

    @Test
    public void testFlags() {
        for (int i = 0; i < 8; i++) {
            boolean hasExtData = (i & 1) > 0;
            boolean hasSegData = (i & 4) > 0;
            boolean hasDataPadding = (i & 16) > 0;
            testSFWithFlag(i, hasExtData, hasSegData, hasDataPadding);
        }
    }

    private void testSFWithFlag(int flag, boolean hasExtData, boolean hasSegData,
            boolean hasDataPadding) {
        byte[] id = ByteUtils.createByteArray(0xD3, 0xA0, 0x88);
        SfIntroducer field = new SfIntroducer(0, 1, id, (byte) flag, 0);
        assertEquals(hasExtData, field.hasExtData());
        assertEquals(hasSegData, field.hasSegmentedData());
        assertEquals(hasDataPadding, field.hasDataPadding());
    }

    @Test
    public void testAllStructuredFieldTypes() {
        // Attributes
        testStructuredFieldTypeIsCorrect(Attribute.MFC, "D3A088");
        testStructuredFieldTypeIsCorrect(Attribute.TLE, "D3A090");

        // Copy Counts
        testStructuredFieldTypeIsCorrect(CopyCount.MCC, "D3A288");
        testStructuredFieldTypeIsCorrect(CopyCount.FNM, "D3A289");

        // Descriptors
        testStructuredFieldTypeIsCorrect(Descriptor.OBD, "D3A66B");
        testStructuredFieldTypeIsCorrect(Descriptor.IID, "D3A67B");
        testStructuredFieldTypeIsCorrect(Descriptor.CPD, "D3A687");
        testStructuredFieldTypeIsCorrect(Descriptor.MDD, "D3A688");
        testStructuredFieldTypeIsCorrect(Descriptor.CDD, "D3A692");
        testStructuredFieldTypeIsCorrect(Descriptor.PTD1, "D3A69B");
        testStructuredFieldTypeIsCorrect(Descriptor.PGD, "D3A6AF");
        testStructuredFieldTypeIsCorrect(Descriptor.GDD, "D3A6BB");
        testStructuredFieldTypeIsCorrect(Descriptor.FGD, "D3A6C5");
        testStructuredFieldTypeIsCorrect(Descriptor.BDD, "D3A6EB");
        testStructuredFieldTypeIsCorrect(Descriptor.IDD, "D3A6FB");

        // Control
        testStructuredFieldTypeIsCorrect(Control.IOC, "D3A77B");
        testStructuredFieldTypeIsCorrect(Control.CPC, "D3A787");
        testStructuredFieldTypeIsCorrect(Control.MMC, "D3A788");
        testStructuredFieldTypeIsCorrect(Control.FNC, "D3A789");
        testStructuredFieldTypeIsCorrect(Control.CFC, "D3A78A");
        testStructuredFieldTypeIsCorrect(Control.CTC, "D3A79B");
        testStructuredFieldTypeIsCorrect(Control.PEC, "D3A7A8");
        testStructuredFieldTypeIsCorrect(Control.PMC, "D3A7AF");

        // Begin BPS("D3A85F", "Begin Page Segment"),
        testStructuredFieldTypeIsCorrect(Begin.BPS, "D3A85F");
        testStructuredFieldTypeIsCorrect(Begin.BCA, "D3A877");
        testStructuredFieldTypeIsCorrect(Begin.BII, "D3A87B");
        testStructuredFieldTypeIsCorrect(Begin.BCP, "D3A887");
        testStructuredFieldTypeIsCorrect(Begin.BFN, "D3A889");
        testStructuredFieldTypeIsCorrect(Begin.BCF, "D3A88A");
        testStructuredFieldTypeIsCorrect(Begin.BOC, "D3A892");
        testStructuredFieldTypeIsCorrect(Begin.BPT, "D3A89B");
        testStructuredFieldTypeIsCorrect(Begin.BDI, "D3A8A7");
        testStructuredFieldTypeIsCorrect(Begin.BDT, "D3A8A8");
        testStructuredFieldTypeIsCorrect(Begin.BNG, "D3A8AD");
        testStructuredFieldTypeIsCorrect(Begin.BPG, "D3A8AF");
        testStructuredFieldTypeIsCorrect(Begin.BGR, "D3A8BB");
        testStructuredFieldTypeIsCorrect(Begin.BDG, "D3A8C4");
        testStructuredFieldTypeIsCorrect(Begin.BFG, "D3A8C5");
        testStructuredFieldTypeIsCorrect(Begin.BRG, "D3A8C6");
        testStructuredFieldTypeIsCorrect(Begin.BOG, "D3A8C7");
        testStructuredFieldTypeIsCorrect(Begin.BAG, "D3A8C9");
        testStructuredFieldTypeIsCorrect(Begin.BMM, "D3A8CC");
        testStructuredFieldTypeIsCorrect(Begin.BFM, "D3A8CD");
        testStructuredFieldTypeIsCorrect(Begin.BRS, "D3A8CE");
        testStructuredFieldTypeIsCorrect(Begin.BSG, "D3A8D9");
        testStructuredFieldTypeIsCorrect(Begin.BMO, "D3A8DF");
        testStructuredFieldTypeIsCorrect(Begin.BBC, "D3A8EB");
        testStructuredFieldTypeIsCorrect(Begin.BIM, "D3A8FB");
        
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

    private void testStructuredFieldTypeIsCorrect(SfType sfType, String id) {
        SfType type = SfTypeFactory.getValue(ByteUtils.hexToBytes(id));
        assertEquals(sfType, type);
    }
}
