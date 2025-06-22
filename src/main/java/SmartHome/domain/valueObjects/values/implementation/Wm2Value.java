package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class Wm2Value implements Value {
    public double value;

    public Wm2Value(double value)
    {
        if ( !isValidPercentage(value) )
            throw new IllegalArgumentException("Invalid wm2 value.");
        this.value = value;
    }

    private boolean isValidPercentage(double value)
    {
        return value >= 0;
    }
}