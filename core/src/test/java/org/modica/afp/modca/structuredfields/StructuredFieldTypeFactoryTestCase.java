/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.modica.afp.modca.structuredfields;

import org.junit.Test;
import org.modica.afp.modca.structuredfields.types.AttributeType;
import org.modica.afp.modca.structuredfields.types.BeginType;
import org.modica.afp.modca.structuredfields.types.CopyCountType;
import org.modica.afp.modca.structuredfields.types.DescriptorType;
import org.modica.afp.modca.structuredfields.types.EndType;
import org.modica.afp.modca.structuredfields.types.MapType;
import org.modica.afp.modca.structuredfields.types.PositionType;
import org.modica.afp.modca.structuredfields.types.ProcessType;
import org.modica.common.ByteUtils;

import static org.junit.Assert.assertEquals;

/**
 * A test case for {@link StructuredFieldTypeFactory}.
 */
public class StructuredFieldTypeFactoryTestCase {
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
        testStructuredFieldTypeIsCorrect(EndType.EPS, "D3A95F");
        testStructuredFieldTypeIsCorrect(EndType.ECA, "D3A977");
        testStructuredFieldTypeIsCorrect(EndType.EII, "D3A97B");
        testStructuredFieldTypeIsCorrect(EndType.ECP, "D3A987");
        testStructuredFieldTypeIsCorrect(EndType.EFN, "D3A989");
        testStructuredFieldTypeIsCorrect(EndType.ECF, "D3A98A");
        testStructuredFieldTypeIsCorrect(EndType.EOC, "D3A992");
        testStructuredFieldTypeIsCorrect(EndType.EPT, "D3A99B");
        testStructuredFieldTypeIsCorrect(EndType.EDI, "D3A9A7");
        testStructuredFieldTypeIsCorrect(EndType.EDT, "D3A9A8");
        testStructuredFieldTypeIsCorrect(EndType.ENG, "D3A9AD");
        testStructuredFieldTypeIsCorrect(EndType.EPG, "D3A9AF");
        testStructuredFieldTypeIsCorrect(EndType.EGR, "D3A9BB");
        testStructuredFieldTypeIsCorrect(EndType.EDG, "D3A9C4");
        testStructuredFieldTypeIsCorrect(EndType.EFG, "D3A9C5");
        testStructuredFieldTypeIsCorrect(EndType.ERG, "D3A9C6");
        testStructuredFieldTypeIsCorrect(EndType.EOG, "D3A9C7");
        testStructuredFieldTypeIsCorrect(EndType.EAG, "D3A9C9");
        testStructuredFieldTypeIsCorrect(EndType.EMM, "D3A9CC");
        testStructuredFieldTypeIsCorrect(EndType.EFM, "D3A9CD");
        testStructuredFieldTypeIsCorrect(EndType.ERS, "D3A9CE");
        testStructuredFieldTypeIsCorrect(EndType.ESG, "D3A9D9");
        testStructuredFieldTypeIsCorrect(EndType.EMO, "D3A9DF");
        testStructuredFieldTypeIsCorrect(EndType.EBC, "D3A9EB");
        testStructuredFieldTypeIsCorrect(EndType.EIM, "D3A9FB");

        // Map
        testStructuredFieldTypeIsCorrect(MapType.MCA, "D3AB77");
        testStructuredFieldTypeIsCorrect(MapType.MMT, "D3AB88");
        testStructuredFieldTypeIsCorrect(MapType.MCF, "D3AB8A");
        testStructuredFieldTypeIsCorrect(MapType.MCD, "D3AB92");
        testStructuredFieldTypeIsCorrect(MapType.MPG, "D3ABAF");
        testStructuredFieldTypeIsCorrect(MapType.MGO, "D3ABBB");
        testStructuredFieldTypeIsCorrect(MapType.MDR, "D3ABC3");
        testStructuredFieldTypeIsCorrect(MapType.IMM, "D3ABCC");
        testStructuredFieldTypeIsCorrect(MapType.MPO, "D3ABD8");
        testStructuredFieldTypeIsCorrect(MapType.MSU, "D3ABEA");
        testStructuredFieldTypeIsCorrect(MapType.MBC, "D3ABEB");
        testStructuredFieldTypeIsCorrect(MapType.MIO, "D3ABFB");
        testStructuredFieldTypeIsCorrect(MapType.FNN, "D3AB89");

        // Position
        testStructuredFieldTypeIsCorrect(PositionType.OBP, "D3AC6B");
        testStructuredFieldTypeIsCorrect(PositionType.ICP, "D3AC7B");
        testStructuredFieldTypeIsCorrect(PositionType.PGP1, "D3ACAF");
        testStructuredFieldTypeIsCorrect(PositionType.FNP, "D3AC89");

        // Process
        testStructuredFieldTypeIsCorrect(ProcessType.PPO, "D3ADC3");
    }

    private void testStructuredFieldTypeIsCorrect(StructuredFieldType sfType, String id) {
        StructuredFieldType type = StructuredFieldTypeFactory.getValue(ByteUtils.hexToBytes(id));
        assertEquals(sfType, type);
    }
}
