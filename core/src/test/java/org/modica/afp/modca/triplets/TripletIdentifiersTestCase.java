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

package org.modica.afp.modca.triplets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A test case for {@link TripletIdentifiers}.
 */
public class TripletIdentifiersTestCase {

    @Test
    public void testIds() {
        testId(0x01, TripletIdentifiers.coded_graphic_character_set_global_identifier);
        testId(0x02, TripletIdentifiers.fully_qualified_name);
        testId(0x04, TripletIdentifiers.mapping_option);
        testId(0x10, TripletIdentifiers.object_classification);
        testId(0x18, TripletIdentifiers.modca_interchange_set);
        testId(0x1F, TripletIdentifiers.font_descriptor_specification);
        testId(0x20, TripletIdentifiers.coded_graphic_character_set_global_identifier1);
        testId(0x21, TripletIdentifiers.object_function_set_specification);
        testId(0x22, TripletIdentifiers.extended_resource_local_identifier);
        testId(0x24, TripletIdentifiers.resource_local_identifier);
        testId(0x25, TripletIdentifiers.resource_section_number);
        testId(0x26, TripletIdentifiers.character_rotation);
        testId(0x2D, TripletIdentifiers.object_byte_offset);
        testId(0x36, TripletIdentifiers.attribute_value);
        testId(0x43, TripletIdentifiers.descriptor_position);
        testId(0x45, TripletIdentifiers.media_eject_control);
        testId(0x46, TripletIdentifiers.page_overlay_conditional_processing);
        testId(0x47, TripletIdentifiers.resource_usage_attribute);
        testId(0x4B, TripletIdentifiers.measurement_units);
        testId(0x4C, TripletIdentifiers.object_area_size);
        testId(0x4D, TripletIdentifiers.area_definition);
        testId(0x4E, TripletIdentifiers.color_specification);
        testId(0x50, TripletIdentifiers.encoding_scheme_id);
        testId(0x56, TripletIdentifiers.medium_map_page_number);
        testId(0x57, TripletIdentifiers.object_byte_extent);
        testId(0x58, TripletIdentifiers.object_structured_field_offset);
        testId(0x59, TripletIdentifiers.object_structured_field_extent);
        testId(0x5A, TripletIdentifiers.object_offset);
        testId(0x5D, TripletIdentifiers.font_horizontal_scale_factor);
        testId(0x5E, TripletIdentifiers.object_count);
        testId(0x62, TripletIdentifiers.object_date_and_timestamp);
        testId(0x63, TripletIdentifiers.resource_management);
        testId(0x64, TripletIdentifiers.object_origin_identifier);
        testId(0x65, TripletIdentifiers.comment);
        testId(0x68, TripletIdentifiers.medium_orientation);
        testId(0x6C, TripletIdentifiers.resource_object_include);
        testId(0x70, TripletIdentifiers.presentation_space_reset_mixing );
        testId(0x71, TripletIdentifiers.presentation_space_mixing_rule);
        testId(0x72, TripletIdentifiers.universal_date_and_timestamp);
        testId(0x74, TripletIdentifiers.toner_saver);
        testId(0x75, TripletIdentifiers.color_fidelity);
        testId(0x78, TripletIdentifiers.font_fidelity);
        testId(0x80, TripletIdentifiers.attribute_qualifier);
        testId(0x81, TripletIdentifiers.page_position_information);
        testId(0x82, TripletIdentifiers.parameter_value);
        testId(0x83, TripletIdentifiers.presentation_control);
        testId(0x84, TripletIdentifiers.font_resolution_and_metric_technology);
        testId(0x85, TripletIdentifiers.finishing_operation);
        testId(0x86, TripletIdentifiers.text_fidelity);
        testId(0x87, TripletIdentifiers.media_fidelity);
        testId(0x88, TripletIdentifiers.finishing_fidelity);
        testId(0x8B, TripletIdentifiers.data_object_font_descriptor);
        testId(0x8C, TripletIdentifiers.locale_selector);
        testId(0x8E, TripletIdentifiers.up3i_finishing_operation);
        testId(0x91, TripletIdentifiers.color_management_resource_descriptor);
        testId(0x95, TripletIdentifiers.rendering_intent);
        testId(0x96, TripletIdentifiers.cmr_tag_fidelity);
        testId(0x97, TripletIdentifiers.device_appearance);
        testId(0x9A, TripletIdentifiers.image_resolution);
        testId(0x9C, TripletIdentifiers.object_container_presentation_space_size);
    }

    private void testId(int expected, TripletIdentifiers tripletId) {
        assertEquals((byte) expected, tripletId.getId());
    }
}
