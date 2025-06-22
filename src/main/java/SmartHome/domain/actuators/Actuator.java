package SmartHome.domain.actuators;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;
import lombok.Getter;

@Getter
public abstract class Actuator implements AggregateRoot<ActuatorId> {
    private final ActuatorName name;
    private final ActuatorId id;
    private final DeviceID deviceID;
    private final ActuatorTypeId actuatorTypeId;
    private final ActuatorModel actuatorModel;

    public Actuator(ActuatorName name, ActuatorId id, DeviceID deviceID, ActuatorTypeId actuatorTypeId,
                    ActuatorModel actuatorModel) {
        new Validations().getActuatorArgsValidation(deviceID, name, id, actuatorTypeId);
        this.name = name;
        this.id = id;
        this.deviceID = deviceID;
        this.actuatorTypeId = actuatorTypeId;
        this.actuatorModel = actuatorModel;
    }

    public ActuatorId identity()
    {
        return this.id;
    }
}
