package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class Dimensions extends ValueObject {
    public final double height;
    public final double width;
    public final double length;

    public Dimensions(double height, double width, double length) {
        validateArguments(height, width, length);
        this.height = height;
        this.width = width;
        this.length = length;
    }

    private void validateArguments(double height, double width, double length) {
        if (Double.isNaN(height) || height <= 0)
            throw new IllegalArgumentException("Invalid arguments passed to constructor.");
        if (Double.isNaN(width) || width <= 0)
            throw new IllegalArgumentException("Invalid arguments passed to constructor.");
        if (Double.isNaN(length) || length <= 0)
            throw new IllegalArgumentException("Invalid arguments passed to constructor.");
    }
}
