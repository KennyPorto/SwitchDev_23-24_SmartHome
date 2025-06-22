package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class WhValue implements Value
{

    public final double currentValue;

    public WhValue(double currentValue) {
        if ( !isValidEnergyConsumption(currentValue) )
            throw new IllegalArgumentException("Invalid Wh value.");
        this.currentValue = currentValue;

    }

    private boolean isValidEnergyConsumption(double value) {
        return value >= 0;
    }

}
