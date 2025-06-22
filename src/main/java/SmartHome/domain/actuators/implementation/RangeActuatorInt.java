package SmartHome.domain.actuators.implementation;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.ValueFactory;
import SmartHome.domain.valueObjects.values.implementation.RangeActuatorIntValue;
import SmartHome.utils.Validations;

import java.util.Objects;

public class RangeActuatorInt extends Actuator
{
    private RangeActuatorIntValue _measurement;
    private LimitInt _limitInt;

    public RangeActuatorInt(DeviceID deviceID, ActuatorName name, ActuatorId id, ActuatorTypeId actuatorTypeId,
                            ActuatorModel actuatorModel, LimitInt limitInt) {
        super(name, id, deviceID, actuatorTypeId, actuatorModel);
        this._limitInt = limitInt;
    }

    public boolean configureActuator(int lowerLimit, int upperLimit, ValueFactory valueFactory) {
        this._limitInt = new LimitInt(lowerLimit, upperLimit);
        _measurement = valueFactory.createRangeActuatorInt(lowerLimit, upperLimit);
        return true;
    }

    public String getReading() {
        if (_measurement == null) throw new IllegalArgumentException("Actuator not configured yet.");
        return _measurement.toString();
    }

    public int getLowerLimit() {
        return _limitInt.lowerLimit;
    }

    public int getUpperLimit() {
        return _limitInt.upperLimit;
    }

    public boolean setMeasurement(String measured) {
        if (_measurement == null) throw new IllegalArgumentException("Actuator not configured yet.");
        try {
            return _measurement.setValue(measured);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean sameAs(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        RangeActuatorInt that = (RangeActuatorInt) object;

        if (_limitInt.lowerLimit != that._limitInt.lowerLimit) return false;
        if (_limitInt.upperLimit != that._limitInt.upperLimit) return false;
        if (! Objects.equals(getName(), that.getName())) return false;
        if (! getId().equals(that.getId())) return false;
        if (! getDeviceID().equals(that.getDeviceID())) return false;
        return Objects.equals(_measurement, that._measurement);
    }
}
