package SmartHome.domain.sensors;

import SmartHome.domain.valueObjects.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static SmartHome.utils.Constants.SENSOR_PATH;

@Service
public class SensorFactory {
    public Sensor createSensor(DeviceID deviceID, SensorModel sensorModel, SensorName name,
                               SensorId sensorID, SensorTypeId sensorTypeId) {
        try {
            String fullPath = SENSOR_PATH + sensorModel.model;
            return (Sensor) Class.forName(fullPath)
                  .getConstructor(DeviceID.class, SensorName.class, SensorId.class, SensorTypeId.class, SensorModel.class)
                  .newInstance(deviceID, name, sensorID, sensorTypeId, sensorModel);
        } catch (ClassNotFoundException | InstantiationException |
                 NoSuchMethodException |
                 InvocationTargetException | IllegalArgumentException |
                 IllegalAccessException exception) {
            return null;
        }
    }

    public Optional<SensorName> createSensorName(String name) {
        try {
            return Optional.of(new SensorName(name));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<SensorModel> createSensorModel(String model) {
        try {
            return Optional.of(new SensorModel(model));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<SensorId> createSensorId(long id) {
        try {
            return Optional.of(new SensorId(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
