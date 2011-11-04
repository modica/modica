package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * The triplet contains the GID of a process element.
 */
public class ProcessElementGid extends FQNCharStringData {

    ProcessElementGid(int length, String data) {
        super(length, data);
    }

    @Override
    public FQNType getFQNType() {
        return FQNType.process_element_gid;
    }
}
