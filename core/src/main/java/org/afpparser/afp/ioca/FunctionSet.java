package org.afpparser.afp.ioca;

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
    FS_10,
    /**
     * Function Set 11 is a superset of Function Set 10, and describes bilevel, grayscale, and
     * color images. This function set is carried by the MO:DCA IS/2 controlling environment.
     */
    FS_11,
    /**
     * Function Set 20 describes bilevel, grayscale, and color images. This function set is carried
     * by the MO:DCA-L controlling environment.
     */
    FS_20,
    /**
     * Function Set 40 is a subset of Function Set 45. It describes tiled images with one bit per
     * spot (color space YCbCr or YCrCb, IDESZ=1). This function set is carried by the MO:DCA-P
     * controlling environment.
     */
    FS_40,
    /**
     * Function Set 42 is a subset of Function Set 45. It describes tiled images with one bit per
     * spot. Images can be either bilevel (color space YCbCr or YCrCb, IDESZ=1) or color (color
     * space CMYK, IDESZ=4). This function set is carried by the MO:DCA-P controlling environment
     */
    FS_42,
    /**
     * Function Set 45 is a superset of Function Set 42. It describes bilevel or color tiled images.
     * This function set is carried by the MO:DCA-P controlling environment.
     */
    FS_45;
}
