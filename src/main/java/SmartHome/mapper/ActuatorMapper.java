package SmartHome.mapper;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuators.implementation.BlindRollerActuator;
import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.actuators.implementation.RangeActuatorInt;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import SmartHome.persistence.jpa.datamodel.ActuatorDataModel;
import SmartHome.persistence.jpa.datamodel.ActuatorRangeDecimalDataModel;
import SmartHome.persistence.jpa.datamodel.ActuatorRangeIntDataModel;
import SmartHome.persistence.jpa.datamodel.BlindRollerDataModel;

import java.util.ArrayList;
import java.util.List;

import static SmartHome.utils.Constants.*;

public class ActuatorMapper
{
    public static ActuatorDTO toDTO(Actuator actuator)
    {
        if ( actuator.getActuatorModel().model.equals(RANGE_INT) ) {
            RangeActuatorInt actuatorInt = (RangeActuatorInt) actuator;
            return new ActuatorDTO(actuator.getName().name, actuator.getActuatorModel().model, actuator.identity().id,
                  actuator.getDeviceID().id, actuator.getActuatorTypeId().name, actuatorInt.getLowerLimit(),
                  actuatorInt.getUpperLimit()
            );
        }

        if ( actuator.getActuatorModel().model.equals(RANGE_DECIMAL) ) {
            RangeActuatorDecimal actuatorDecimal = (RangeActuatorDecimal) actuator;
            return new ActuatorDTO(actuator.getName().name, actuator.getActuatorModel().model, actuator.identity().id,
                  actuator.getDeviceID().id, actuator.getActuatorTypeId().name, actuatorDecimal.getLowerLimit(),
                  actuatorDecimal.getUpperLimit()
            );
        }

        if ( actuator.getActuatorModel().model.equals(BLIND_ROLLER) ) {
            BlindRollerActuator blindRoller = (BlindRollerActuator) actuator;
            return new ActuatorDTO(actuator.getName().name, actuator.getActuatorModel().model, actuator.identity().id,
                  actuator.getDeviceID().id, actuator.getActuatorTypeId().name, blindRoller.getCurrentPercentage().value
            );
        }

        return new ActuatorDTO(actuator.getName().name, actuator.getActuatorModel().model, actuator.identity().id,
              actuator.getDeviceID().id, actuator.getActuatorTypeId().name);
    }

    public static Iterable<ActuatorDTO> toDTO(Iterable<Actuator> actuators)
    {
        List<ActuatorDTO> actuatorDTOS = new ArrayList<>();
        actuators.forEach(
              actuator -> actuatorDTOS.add(
                    toDTO(actuator)
              ));
        return actuatorDTOS;
    }

    public static Actuator dataModelToDomain(ActuatorDataModel dataModel)
    {
        ActuatorId id = new ActuatorId(dataModel.getActuatorId());
        ActuatorName name = new ActuatorName(dataModel.getActuatorName());
        ActuatorTypeId type = new ActuatorTypeId(dataModel.getActuatorTypeId());
        DeviceID deviceId = new DeviceID(dataModel.getDeviceId());
        ActuatorModel actuatorModel = new ActuatorModel(dataModel.getActuatorModel());

        if ( actuatorModel.model.equals(RANGE_INT) ) {
            ActuatorRangeIntDataModel actuator = (ActuatorRangeIntDataModel) dataModel;
            LimitInt limitInt = new LimitInt(actuator.getLowerLimit(), actuator.getUpperLimit());
            return new ActuatorFactory().createActuator(deviceId, actuatorModel, name, id, type, limitInt);
        }

        if ( actuatorModel.model.equals(RANGE_DECIMAL) ) {
            ActuatorRangeDecimalDataModel actuator = (ActuatorRangeDecimalDataModel) dataModel;
            LimitFractional limitFractional = new LimitFractional(actuator.getLowerLimit(), actuator.getUpperLimit());
            return new ActuatorFactory().createActuator(deviceId, actuatorModel, name, id, type, limitFractional);
        }

        if ( actuatorModel.model.equals(BLIND_ROLLER) ) {
            Actuator actuator = new ActuatorFactory().createActuator(deviceId, actuatorModel, name, id, type);
            BlindRollerActuator blindRollerActuator = (BlindRollerActuator) actuator;

            int currentValue = ((BlindRollerDataModel) dataModel).getCurrentValue();
            PercentageValue percentageValue = new PercentageValue(currentValue);
            blindRollerActuator.setCurrentPercentage(percentageValue);
            return blindRollerActuator;
        }

        return new ActuatorFactory().createActuator(deviceId, actuatorModel, name, id, type);
    }

    public static Iterable<Actuator> dataModelListToDomain(Iterable<ActuatorDataModel> dataModels)
    {
        List<Actuator> actuators = new ArrayList<>();
        dataModels.forEach(
              dataModel -> actuators.add(dataModelToDomain(dataModel))
        );
        return actuators;
    }
}