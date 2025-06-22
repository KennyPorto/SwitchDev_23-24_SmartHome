package SmartHome.domain.actuators.implementation;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

public class SwitchOnOffActuator extends Actuator
{
    private boolean _isOn;

    public SwitchOnOffActuator(DeviceID deviceID, ActuatorName name, ActuatorId id, ActuatorTypeId actuatorTypeId,
                               ActuatorModel actuatorModel) {
        super(name, id, deviceID, actuatorTypeId, actuatorModel);
        this._isOn = false;
    }

    public boolean switchActuator() {
        this._isOn = ! this._isOn;
        return this._isOn;
    }

    public boolean isOn() {
        return this._isOn;
    }


    public String getReading() {
        return String.valueOf(_isOn);
    }

    @Override
    public boolean sameAs(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        SwitchOnOffActuator that = (SwitchOnOffActuator) object;

        if (_isOn != that._isOn) return false;
        if (! getName().equals(that.getName())) return false;
        if (! getId().equals(that.getId())) return false;
        if (! getDeviceID().equals(that.getDeviceID())) return false;
        return getActuatorTypeId().equals(that.getActuatorTypeId());
    }

}
