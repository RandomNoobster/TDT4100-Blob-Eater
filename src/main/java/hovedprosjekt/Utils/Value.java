package hovedprosjekt.Utils;

public class Value {
    private int value = 0;
    private Integer minValue;
    private Integer maxValue;

    /**
     * Creates a new {@link Value} object.
     */
    public Value() {
    }

    /**
     * Creates a new {@link Value} object.
     * @param value
     */
    public Value(int value) {
        setValue(value);
    }

    /**
     * Creates a new {@link Value} object.
     * @param value
     * @param minValue
     * @param maxValue
     */
    public Value(int value, Integer minValue, Integer maxValue) {
        setValue(value);
        setMinValue(minValue);
        setMaxValue(maxValue);
    }

    /**
     * Sets the value of the {@link Value} object.
     * @param value
     */
    public void setValue(int value) {
        if (minValue != null && value < minValue) {
            throw new IllegalArgumentException("Value cannot be less than " + minValue);
        }
        if (maxValue != null && value > maxValue) {
            throw new IllegalArgumentException("Value cannot be greater than " + maxValue);
        }
        this.value = value;
    }

    /**
     * Increments the value of the {@link Value} object by the given value.
     * @param value
     */
    public void incrementValue(int value) {
        if (minValue != null && this.value + value < minValue) {
            throw new IllegalArgumentException("Value cannot be less than " + minValue);
        } else if (maxValue != null && this.value + value > maxValue) {
            throw new IllegalArgumentException("Value cannot be greater than " + maxValue);
        }
        this.value += value;
    }

    /**
     * @return the value of the {@link Value} object.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @return the minimum allowed value of the {@link Value} object.
     */
    public Integer getMinValue() {
        return this.minValue;
    }

    /**
     * @return the maximum allowed value of the {@link Value} object.
     */
    public Integer getMaxValue() {
        return this.maxValue;
    }

    /**
     * Sets the minimum allowed value of the {@link Value} object.
     * @param minValue
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * Sets the maximum allowed value of the {@link Value} object.
     * @param maxValue
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
