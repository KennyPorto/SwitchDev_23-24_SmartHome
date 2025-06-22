package SmartHome.domain.actuators;

import SmartHome.domain.valueObjects.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static SmartHome.utils.Constants.ACTUATOR_PATH;

@Service
public class ActuatorFactory {
    public Actuator createActuator(DeviceID deviceID, ActuatorModel actuatorModel, ActuatorName name, ActuatorId id,
                                   ActuatorTypeId actuatorTypeId) {
        try {
            String fullPath = ACTUATOR_PATH + actuatorModel.model;

            return (Actuator) Class.forName(fullPath)
                    .getConstructor(DeviceID.class, ActuatorName.class, ActuatorId.class, ActuatorTypeId.class, ActuatorModel.class)
                    .newInstance(deviceID, name, id, actuatorTypeId, actuatorModel);
        } catch (ClassNotFoundException | InstantiationException |
                 NoSuchMethodException | NullPointerException |
                 InvocationTargetException | IllegalArgumentException |
                 IllegalAccessException exception) {
            return null;
        }
    }

    public Actuator createActuator(DeviceID deviceID, ActuatorModel actuatorModel, ActuatorName name, ActuatorId id,
                                   ActuatorTypeId actuatorTypeId, LimitInt limitInt) {
        try {
            String fullPath = ACTUATOR_PATH + actuatorModel.model;

            return (Actuator) Class.forName(fullPath)
                    .getConstructor(DeviceID.class, ActuatorName.class, ActuatorId.class, ActuatorTypeId.class,
                            ActuatorModel.class, LimitInt.class)
                    .newInstance(deviceID, name, id, actuatorTypeId, actuatorModel, limitInt);
        } catch (ClassNotFoundException | InstantiationException |
                 NoSuchMethodException | NullPointerException |
                 InvocationTargetException | IllegalArgumentException |
                 IllegalAccessException exception) {
            return null;
        }
    }

    public Actuator createActuator(DeviceID deviceID, ActuatorModel actuatorModel, ActuatorName name, ActuatorId id,
                                   ActuatorTypeId actuatorTypeId, LimitFractional limitFractional) {
        try {
            String fullPath = ACTUATOR_PATH + actuatorModel.model;

            return (Actuator) Class.forName(fullPath)
                    .getConstructor(DeviceID.class, ActuatorName.class, ActuatorId.class, ActuatorTypeId.class,
                            LimitFractional.class, ActuatorModel.class)
                    .newInstance(deviceID, name, id, actuatorTypeId, limitFractional, actuatorModel);
        } catch (ClassNotFoundException | InstantiationException |
                 NoSuchMethodException | NullPointerException |
                 InvocationTargetException | IllegalArgumentException |
                 IllegalAccessException exception) {
            return null;
        }
    }

    public Optional<ActuatorId> createActuatorId(long id) {
        try {
            return Optional.of(new ActuatorId(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ActuatorName> createActuatorName(String name) {
        try {
            return Optional.of(new ActuatorName(name));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ActuatorModel> createActuatorModel(String model) {
        try {
            return Optional.of(new ActuatorModel(model));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
