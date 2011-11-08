package org.afpparser.afp.modca;


/**
 * The category codes for structured fields.
 */
public enum CategoryCode {
    /** page segment */
    page_segment(0x5F),
    /** object area */
    object_area(0x6B),
    /** color attribute table */
    color_attribute_table(0x77),
    /** im image */
    im_image(0x7B),
    /** medium */
    medium(0x88),
    /** coded font */
    coded_font(0x8A),
    /** process element */
    process_element(0x90),
    /** object container */
    object_container(0x92),
    /** presentation text */
    presentation_text(0x9B),
    /** index */
    index(0xA7),
    /** document */
    document(0xA8),
    /** page group */
    page_group(0xAD),
    /** page */
    page(0xAF),
    /** graphics */
    graphics(0xBB),
    /** data resource */
    data_resource(0xC3),
    /** document environment group (deg) */
    document_environment_group(0xC4),
    /** form environment group */
    form_environment_group(0xC5),
    /** resource group */
    resource_group(0xC6),
    /** object environment group (oeg) */
    object_environment_group(0xC7),
    /** active environment group (aeg) */
    active_environment_group(0xC9),
    /** medium map */
    medium_map(0xCC),
    /** form map */
    form_map(0xCD),
    /** name resource */
    name_resource(0xCE),
    /** page overlay */
    page_overlay(0xD8),
    /** resource environment group (reg) */
    resource_enviroment_group(0xD9),
    /** overlay */
    overlay(0xDF),
    /** data suppression */
    data_supression(0xEA),
    /** bar code */
    barcode(0xEB),
    /** no operation */
    no_operation(0xEE),
    /** image */
    image(0xFB),
    //FOCA
    /** font */
    font(0x89),
    /** code page */
    code_page(0x87);

    private final byte code;

    private CategoryCode(int code) {
        this.code = (byte) code;
    }

    public byte getValue() {
        return code;
    }
}
