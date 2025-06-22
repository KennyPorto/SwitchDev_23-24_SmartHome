package SmartHome.domain.actuators.implementation;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.ValueFactory;
import SmartHome.domain.valueObjects.values.implementation.RangeActuatorFractionalValue;
import SmartHome.utils.Validations;

public class RangeActuatorDecimal extends Actuator
{
    private RangeActuatorFractionalValue _measurement;
    private LimitFractional _limitFractional;
    private Precision _precision;

    public RangeActuatorDecimal(DeviceID deviceID, ActuatorName name, ActuatorId id, ActuatorTypeId actuatorTypeId,
                                LimitFractional limitFractional, ActuatorModel actuatorModel) {
        super(name, id, deviceID, actuatorTypeId, actuatorModel);
        this._limitFractional = limitFractional;
        this._precision = new Precision(0.1);
    }

    public boolean configureActuator(double lowerLimit, double upperLimit, double precision, ValueFactory valueFactory) {
        this._limitFractional = new LimitFractional(lowerLimit, upperLimit);
        this._precision = new Precision(precision);
        this._measurement = valueFactory.createRangeActuatorDecimal(lowerLimit, upperLimit);
        return true;
    }

    public String getReading() {
        if (_measurement == null) throw new IllegalArgumentException("Actuator not configured yet.");
        return _measurement.toString();
    }

    public boolean setMeasurement(String measured) {
        if (_measurement == null)
            throw new IllegalArgumentException("Actuator not configured yet.");
        try {
            return _measurement.setValue(measured);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public double getLowerLimit() {
        return _limitFractional.lowerLimit;
    }

    public double getUpperLimit() {
        return _limitFractional.upperLimit;
    }

    public double getPrecision() {
        return _precision.precision;
    }

    @Override
    public boolean sameAs(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        RangeActuatorDecimal that = (RangeActuatorDecimal) object;

        if (Double.compare(_limitFractional.lowerLimit, that._limitFractional.lowerLimit) != 0) return false;
        if (Double.compare(_limitFractional.upperLimit, that._limitFractional.upperLimit) != 0) return false;
        if (Double.compare(_precision.precision, that._precision.precision) != 0) return false;
        if (! getDeviceID().equals(that.getDeviceID())) return false;
        return getDeviceID().equals(that.getDeviceID());
    }
}
