package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains a GID reference to a Color Management Resource (CMR). CMRs specify color
 * management information that is used to render a document component. The GID is the CMR name
 * that is specified in the CMR header for the resource. CMRs are defined in the Color Management
 * Object Content Architecture (CMOCA) Reference.
 */
public class ColorManagementResource extends FQNCharStringData {
    public ColorManagementResource(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.color_management_resource_ref;
    }
}
