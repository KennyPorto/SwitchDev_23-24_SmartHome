package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.ddd.ValueObject;
import SmartHome.domain.valueObjects.values.Value;
import lombok.EqualsAndHashCode;

public class PercentageValue extends ValueObject implements Value
{
    public final int value;

    public PercentageValue(int currentValue)
    {
        if ( !isValidPercentage(currentValue) )
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        this.value = currentValue;
    }

    private boolean isValidPercentage(int value)
    {
        return value >= 0 && value <= 100;
    }
}