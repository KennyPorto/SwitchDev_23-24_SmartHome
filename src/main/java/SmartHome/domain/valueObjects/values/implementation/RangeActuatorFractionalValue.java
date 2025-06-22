package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class RangeActuatorFractionalValue implements Value {
    private final double _lowerLimit;
    private final double _upperLimit;
    private double _measurement = 0.0;

    public RangeActuatorFractionalValue(double lowerLimit, double upperLimit) {
        this._lowerLimit = lowerLimit;
        this._upperLimit = upperLimit;
    }

    public boolean setValue(String measured) {
        double measurementValue = Double.parseDouble(measured);
        if (measurementValue < _lowerLimit) return false;
        if (measurementValue > _upperLimit) return false;
        _measurement = measurementValue;
        return true;
    }
}
