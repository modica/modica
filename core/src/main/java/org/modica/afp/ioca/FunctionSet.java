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

package org.modica.afp.ioca;

import org.modica.common.ByteUtils;

/**
 * A function set is a set of self-defining fields that describes an image object. Specifically, it
 * is a definition of the image segment: which parameters the image segment should consist of, and
 * what values each parameter should have. The image object described in the function set can thus
 * be processed in different controlling environments.
 * <p>
 * Each function set has an identification. With that identification, products determine the level
 * of support they must provide to generate or receive IOCA image objects.
 * </p>
 */
public enum FunctionSet {
    /**
     * Function Set 10 describes bilevel images. This function set is carried by the MO:DCA-P and
     * IPDS controlling environments.
     */
    FS_10(0x0A),
    /**
     * Function Set 11 is a superset of Function Set 10, and describes bilevel, grayscale, and
     * color images. This function set is carried by the MO:DCA IS/2 controlling environment.
     */
    FS_11(0x0B),
    /**
     * Function Set 20 describes bilevel, grayscale, and color images. This function set is carried
     * by the MO:DCA-L controlling environment.
     */
    FS_20(0x14),
    /**
     * Function Set 40 is a subset of Function Set 45. It describes tiled images with one bit per
     * spot (color space YCbCr or YCrCb, IDESZ=1). This function set is carried by the MO:DCA-P
     * controlling environment.
     */
    FS_40(0x28),
    /**
     * Function Set 42 is a subset of Function Set 45. It describes tiled images with one bit per
     * spot. Images can be either bilevel (color space YCbCr or YCrCb, IDESZ=1) or color (color
     * space CMYK, IDESZ=4). This function set is carried by the MO:DCA-P controlling environment
     */
    FS_42(0x2A),
    /**
     * Function Set 45 is a superset of Function Set 42. It describes bilevel or color tiled images.
     * This function set is carried by the MO:DCA-P controlling environment.
     */
    FS_45(0x2D);
    
    private final byte value;
    
    private FunctionSet(int value) {
    	this.value = (byte) value;
    }
    
    static FunctionSet getValue(byte byteValue) {
    	for (FunctionSet fs:  FunctionSet.values()) {
    		if (fs.value == byteValue) {
    			return fs;
    		}
    	}
    	throw new IllegalArgumentException(ByteUtils.bytesToHex(byteValue) + " is not a valid IOCA function set");
    }
}
