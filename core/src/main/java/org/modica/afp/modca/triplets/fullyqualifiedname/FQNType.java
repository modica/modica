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

package org.modica.afp.modca.triplets.fullyqualifiedname;

import java.util.HashMap;
import java.util.Map;

public enum FQNType {
    /** This GID replaces The first parameter in The structured field that contains a gid name. */
    replace_first_gid_name(0x01),
    /** This triplet contains The name of a font family. */
    font_family_name(0x07),
    /** This triplet contains The name of a font typeface. */
    font_typeface_name(0x08),
    /** This triplet specifies a reference to The mo:dca resource hierarchy. */
    modca_resource_hierarchy_ref(0x09),
    /** The triplet contains a gid reference to a begin resource group structured field. */
    begin_resource_group_ref(0x0A),
    /** The triplet contains a gid reference to a document attribute. */
    attribute_gid(0x0B),
    /** The triplet contains The gid of a process element. */
    process_element_gid(0x0C),
    /** The triplet contains a reference to a begin page group structured field. */
    begin_page_group_ref(0x0D),
    /** The triplet contains a reference to a media type. */
    media_type_ref(0x11),
    /** The triplet contains a reference to a color management resource. */
    color_management_resource_ref(0x41),
    /** The triplet contains a reference to a data-object font file that defines a base font. */
    data_object_font_base_font_id(0x6E),
    /** The triplet contains a reference to a data-object font file that defines a linked font. */
    data_object_font_linked_font_id(0x7E),
    /** The triplet contains a reference to a begin document structured field. */
    begin_document_ref(0x83),
    /**
     * The triplet contains a reference to a begin structured field associated with a resource;
     * or contains a gid reference to a coded font.
     */
    begin_resource_object_ref(0x84),
    /**
     * The triplet contains a gid reference to a code page that specifies The code points and
     * graphic character names for a coded font.
     */
    code_page_name_ref(0x85),
    /**
     * The triplet contains a gid name reference to a font character set that specifies
     * a set of graphics characters.
     */
    font_charset_name_ref(0x86),
    /** The triplet contains a gid reference to a begin page structured field. */
    begin_page_ref(0x87),
    /** The triplet contains a gid reference to a begin medium map structured field. */
    begin_medium_map_ref(0x8D),
    /**
     * The triplet contains a gid reference to a coded font, which identifies a specific
     * code page and a specific font character set.
     */
    coded_font_name_ref(0x8E),
    /** The triplet contains a gid reference to a begin document index structured field. */
    begin_document_index_ref(0x98),
    /** The triplet contains a gid reference to a begin overlay structured field. */
    begin_overlay_ref(0xB0),
    /** The triplet contains a gid reference to a resource used by a data object. */
    data_object_internal_resource_ref(0xBE),
    /** The triplet contains a gid reference to an index element structured field. */
    index_element_gid(0xCA),
    /**
     * The triplet contains a reference to oTher object data which may or may
     * not be defined by an ibm presentation architecture.
     */
    other_object_data_ref(0xCE),
    /**
     * The triplet contains a reference to a resource used by a data object. The gid may be a
     * filename or any oTher identifier associated with the resource and is used to located The
     * resource object in The resource hierarchy. The data object that uses The resource may or may
     * not be defined by an ibm presentation architecture.
     */
    data_object_external_resource_ref(0xDE),
    // gid format
    /** The gid is a character encoded name. */
    format_charstr(0x00),
    /** The gid is a asn.1 object identifier (oid). */
    format_oid(0x10),
    /** The gid is a uniform resource locator (url). */
    format_url(0x20);
    private final byte id;

    private static final Map<Byte, FQNType> CACHE = new HashMap<Byte, FQNType>();

    static {
        for (FQNType type : FQNType.values()) {
            CACHE.put(type.getId(), type);
        }
    }

    private FQNType(int id) {
        this.id = (byte) id;
    }

    public final byte getId() {
        return id;
    }

    public static FQNType getValue(byte id) {
        return CACHE.get(id);
    }
}
