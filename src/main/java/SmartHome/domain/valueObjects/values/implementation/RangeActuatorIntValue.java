package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class RangeActuatorIntValue implements Value {
    private final int _lowerLimit;
    private final int _upperLimit;
    private int _measurement = 0;

    public RangeActuatorIntValue(int lowerLimit, int upperLimit) {
        this._lowerLimit = lowerLimit;
        this._upperLimit = upperLimit;
    }

    public boolean setValue(String measured) {
        int measurementValue = Integer.parseInt(measured);
        if (measurementValue < _lowerLimit) return false;
        if (measurementValue > _upperLimit) return false;
        _measurement = measurementValue;
        return true;
    }
}
