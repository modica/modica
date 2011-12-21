package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * A Fully Qualified Name that contains a Global Resource Identification.
 */
public class FQNGridData extends FullyQualifiedName {
    private final FQNType type;
    private final GlobalResourceId grid;

    FQNGridData(int length, GlobalResourceId grid, FQNType type) {
        super(length);
        this.grid = grid;
        this.type = type;
    }

    @Override
    public FQNType getFQNType() {
        return type;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.character_string;
    }

    /**
     * Returns the Global Resource Identification data.
     *
     * @return the grid
     */
    public GlobalResourceId getGrid() {
        return grid;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNGridData)) {
            return false;
        }
        FQNGridData obj = (FQNGridData) o;
        return this.getLength() == obj.getLength()
                && this.grid.equals(obj.grid)
                && this.type == obj.type;
    }

    @Override
    public int hashCode() {
        int ret = 17;
        ret = 31 * ret + getLength();
        ret = 31 * ret + grid.hashCode();
        ret = 31 * ret + type.hashCode();
        return ret;
    }

    @Override
    public String getValueAsString() {
        return "GlobalResourceId=[" + grid.toString() + "]";
    }

}
