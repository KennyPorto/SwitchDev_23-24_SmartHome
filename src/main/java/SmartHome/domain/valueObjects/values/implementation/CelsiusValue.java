package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class CelsiusValue implements Value {
    public double value;

    public CelsiusValue(double value) {
        this.value = value;
    }
}
