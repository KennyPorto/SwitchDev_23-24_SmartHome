package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class WValue implements Value {
    public final double currentValue;

    public WValue(double currentValue) {
        if ( !isValidPowerConsumption(currentValue) )
            throw new IllegalArgumentException("Invalid W value.");
        this.currentValue = currentValue;
    }

    private boolean isValidPowerConsumption(double value) {
        return value >= 0;
    }

}
