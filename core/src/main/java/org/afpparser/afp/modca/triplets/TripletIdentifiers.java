package org.afpparser.afp.modca.triplets;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of the structured field triplet identifiers.
 */
public enum TripletIdentifiers {
    coded_graphic_character_set_global_identifier(0x01),
    fully_qualified_name(0x02),
    mapping_option(0x04),
    object_classification(0x10),
    modca_interchange_set(0x18),
    font_descriptor_specification(0x1f),
    coded_graphic_character_set_global_identifier1(0x20),
    object_function_set_specification(0x21),
    extended_resource_local_identifier(0x22),
    resource_local_identifier(0x24),
    resource_section_number(0x25),
    character_rotation(0x26),
    object_byte_offset(0x2d),
    attribute_value(0x36),
    descriptor_position(0x43),
    media_eject_control(0x45),
    page_overlay_conditional_processing(0x46),
    resource_usage_attribute(0x47),
    measurement_units(0x4b),
    object_area_size(0x4c),
    area_definition(0x4d),
    color_specification(0x4e),
    encoding_scheme_id(0x50),
    medium_map_page_number(0x56),
    object_byte_extent(0x57),
    object_structured_field_offset(0x58),
    object_structured_field_extent(0x59),
    object_offset(0x5a),
    font_horizontal_scale_factor(0x5d),
    object_count(0x5e),
    object_date_and_timestamp(0x62),
    resource_management(0x63),
    object_origin_identifier(0x64),
    comment(0x65),
    medium_orientation(0x68),
    resource_object_include(0x6c),
    presentation_space_reset_mixing(0x70),
    presentation_space_mixing_rule(0x71),
    universal_date_and_timestamp(0x72),
    toner_saver(0x74),
    color_fidelity(0x75),
    font_fidelity(0x78),
    attribute_qualifier(0x80),
    page_position_information(0x81),
    parameter_value(0x82),
    presentation_control(0x83),
    font_resolution_and_metric_technology(0x84),
    finishing_operation(0x85),
    text_fidelity(0x86),
    media_fidelity(0x87),
    finishing_fidelity(0x88),
    data_object_font_descriptor(0x8b),
    locale_selector(0x8c),
    up3i_finishing_operation(0x8e),
    color_management_resource_descriptor(0x91),
    rendering_intent(0x95),
    cmr_tag_fidelity(0x96),
    device_appearance(0x97);

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

    public byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static int getmaxlength() {
        return MAX_LENGTH;
    }

    public static TripletIdentifiers getTripletId(byte tId) {
        return CACHE.get(tId);
    }
}
