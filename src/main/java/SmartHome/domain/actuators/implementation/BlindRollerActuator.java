package SmartHome.domain.actuators.implementation;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import SmartHome.utils.Validations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlindRollerActuator extends Actuator
{
    private PercentageValue currentPercentage;

    public BlindRollerActuator(DeviceID deviceID, ActuatorName name, ActuatorId id, ActuatorTypeId actuatorTypeId,
                               ActuatorModel actuatorModel) {
        super(name, id, deviceID, actuatorTypeId, actuatorModel);
        this.currentPercentage = new PercentageValue(100);;
    }

    public BlindRollerActuator setCurrentPercentage(PercentageValue currentPercentage) {
        this.currentPercentage = currentPercentage;
        return this;
    }

    @Override
    public boolean sameAs(Object o)
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        BlindRollerActuator that = (BlindRollerActuator) o;

        if ( !getName().equals(that.getName()) ) return false;
        if ( !getId().equals(that.getId()) ) return false;
        if ( !getDeviceID().equals(that.getDeviceID()) ) return false;
        return getActuatorTypeId().equals(that.getActuatorTypeId());
    }
}

