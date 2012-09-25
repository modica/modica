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

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modica.afp.modca.Context;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.triplets.foca.ResourceManagement;
import org.modica.afp.modca.triplets.fullyqualifiedname.FullyQualifiedName;

/**
 * Enumeration of the structured field triplet identifiers.
 */
public enum TripletIdentifiers {
    coded_graphic_character_set_global_identifier(0x01) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return Cgcsgid.parse(params, context);
        }
    },
    fully_qualified_name(0x02) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context)
                throws UnsupportedEncodingException, MalformedURLException {
            return FullyQualifiedName.parse(params, length);
        }
    },
    mapping_option(0x04) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new MappingOption(params.getByte());
        }
    },
    object_classification(0x10) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    modca_interchange_set(0x18) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    font_descriptor_specification(0x1f) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    coded_graphic_character_set_global_identifier1(0x20) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_function_set_specification(0x21) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new ObjectFunctionSetSpecification(params, length);
        }
    },
    extended_resource_local_identifier(0x22) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    resource_local_identifier(0x24) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new ResourceLocalId(params);
        }
    },
    resource_section_number(0x25) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    character_rotation(0x26) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new CharacterRotation(params);
        }
    },
    object_byte_offset(0x2d) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    attribute_value(0x36) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context)
                throws UnsupportedEncodingException {
            return new AttributeValue(length, this, params);
        }
    },
    descriptor_position(0x43) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new DescriptorPosition(params.getByte());
        }
    },
    media_eject_control(0x45) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    page_overlay_conditional_processing(0x46) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    resource_usage_attribute(0x47) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    measurement_units(0x4b) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new MeasurementUnits(params);
        }
    },
    object_area_size(0x4c) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new ObjectAreaSize(params);
        }
    },
    area_definition(0x4d) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    color_specification(0x4e) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    encoding_scheme_id(0x50) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    medium_map_page_number(0x56) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_byte_extent(0x57) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_structured_field_offset(0x58) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_structured_field_extent(0x59) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_offset(0x5a) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    font_horizontal_scale_factor(0x5d) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_count(0x5e) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_date_and_timestamp(0x62) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    resource_management(0x63) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return ResourceManagement.parse(params);
        }
    },
    object_origin_identifier(0x64) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    comment(0x65) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    medium_orientation(0x68) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    resource_object_include(0x6c) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    presentation_space_reset_mixing(0x70) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    presentation_space_mixing_rule(0x71) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    universal_date_and_timestamp(0x72) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    toner_saver(0x74) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    color_fidelity(0x75) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    font_fidelity(0x78) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    attribute_qualifier(0x80) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    page_position_information(0x81) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    parameter_value(0x82) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    presentation_control(0x83) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    font_resolution_and_metric_technology(0x84) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    finishing_operation(0x85) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    text_fidelity(0x86) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    media_fidelity(0x87) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    finishing_fidelity(0x88) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    data_object_font_descriptor(0x8b) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    locale_selector(0x8c) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    up3i_finishing_operation(0x8e) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    color_management_resource_descriptor(0x91) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    rendering_intent(0x95) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    cmr_tag_fidelity(0x96) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    device_appearance(0x97) {
        @Override
        public Triplet buildTriplet(int length, Parameters params, Context context) {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    image_resolution(0x9A) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context)
                throws UnsupportedEncodingException, MalformedURLException {
            return new NotYetImplementedTriplet(length, this, params);
        }
    },
    object_container_presentation_space_size(0x9C) {
        @Override
        Triplet buildTriplet(int length, Parameters params, Context context)
                throws UnsupportedEncodingException, MalformedURLException {
            return new NotYetImplementedTriplet(length, this, params);
        }
    };

    private static final int MAX_LENGTH = 254;

    private final byte id;
    private final String name;

    private static final Map<Byte, TripletIdentifiers> CACHE = new HashMap<Byte, TripletIdentifiers>();

    static {
        for (TripletIdentifiers type : TripletIdentifiers.values()) {
            CACHE.put(type.getId(), type);
        }
    }

    private TripletIdentifiers(int id) {
        this.id = (byte) id;
        this.name = this.toString();
    }

    byte getId() {
        return id;
    }

    String getName() {
        return name;
    }

    public static int getMaxLength() {
        return MAX_LENGTH;
    }

    static TripletIdentifiers getTripletId(byte tId) {
        return CACHE.get(tId);
    }

    abstract Triplet buildTriplet(int length, Parameters params, Context context)
            throws UnsupportedEncodingException, MalformedURLException;

    public static final class NotYetImplementedTriplet extends Triplet {

        private final int length;
        private final TripletIdentifiers tId;

        private NotYetImplementedTriplet(int length, TripletIdentifiers tId, Parameters params) {
            this.length = length;
            this.tId = tId;
            params.skip(length - 2);
        }

        @Override
        public int getLength() {
            return length;
        }

        @Override
        public TripletIdentifiers getTid() {
            return tId;
        }

        @Override
        public List<ParameterAsString> getParameters() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

        @Override
        public int hashCode() {
            return this.hashCode();
        }
    }
}
