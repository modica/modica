package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet specifies a reference to the MO:DCA resource hierarchy. The normal MO:DCA resource
 * search order should be used for resolving a resource object reference when this triplet is
 * specified.
 */
public class ModcaResourceHierarchyRef extends FQNCharStringData {

    ModcaResourceHierarchyRef(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.modca_resource_hierarchy_ref;
    }

}
