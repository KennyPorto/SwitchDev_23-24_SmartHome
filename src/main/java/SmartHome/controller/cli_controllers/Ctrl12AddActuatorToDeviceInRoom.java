package SmartHome.controller.cli_controllers;

import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.Value;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import SmartHome.mapper.ActuatorDTO;
import SmartHome.service.ActuatorService;

import static SmartHome.utils.Constants.RANGE_DECIMAL;
import static SmartHome.utils.Constants.RANGE_INT;

public class Ctrl12AddActuatorToDeviceInRoom
{
    private final ActuatorService actuatorService;

    public Ctrl12AddActuatorToDeviceInRoom(ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }

    public boolean addActuator(ActuatorDTO actuatorDTO)
    {

        try {
            ActuatorId actuatorId = new ActuatorId(actuatorDTO.actuatorId);
            ActuatorModel actuatorModel = new ActuatorModel(actuatorDTO.model);
            DeviceID deviceId = new DeviceID(actuatorDTO.deviceId);
            ActuatorTypeId actuatorTypeId = new ActuatorTypeId(actuatorDTO.actuatorType);
            ActuatorName actuatorName = new ActuatorName(actuatorDTO.name);
            LimitFractional limitFractional = new LimitFractional(0.0, 1.0);
            LimitInt limitInt = new LimitInt(0, 1);
            Value percentageValue = new PercentageValue(actuatorDTO.currentValue);

            if (actuatorDTO.model.equals(RANGE_INT)) {
                limitInt = new LimitInt(actuatorDTO.lowerLimit, actuatorDTO.upperLimit);
            }
            if (actuatorDTO.model.equals(RANGE_DECIMAL)) {
                limitFractional = new LimitFractional(actuatorDTO.lowerLimitFractional, actuatorDTO.upperLimitFractional);
            }

            return actuatorService
                    .add(deviceId, actuatorModel, actuatorName, actuatorId, actuatorTypeId,
                          limitInt, limitFractional, percentageValue) != null;
        } catch (Exception e) {
            return false;
        }
    }

}
