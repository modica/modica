package org.modica.afp.modca;

/**
 * An object that wraps parameter names and values.
 */
public class ParameterAsString {

    private final String key;
    private final String value;

    public ParameterAsString(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Helper constructor that performs String.valueOf() on the value object.
     * @param key the key
     * @param value is converted to a String
     */
    public ParameterAsString(String key, Object value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " = " + value;
    }
}
