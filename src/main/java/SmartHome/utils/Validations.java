package SmartHome.utils;

import SmartHome.domain.valueObjects.*;

public class Validations {
    public void getSensorArgsValidation(DeviceID deviceID, SensorName name, SensorId sensorID,
                                               SensorTypeId sensorTypeId) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null or empty");
        if (sensorID == null)
            throw new IllegalArgumentException("SensorId cannot be null");
        if (deviceID == null)
            throw new IllegalArgumentException("DeviceID cannot be null");
        if (sensorTypeId == null)
            throw new IllegalArgumentException("SensorTypeId cannot be null");
    }

    public void getActuatorArgsValidation(DeviceID deviceID, ActuatorName name, ActuatorId id,
                                                 ActuatorTypeId actuatorTypeId) {
        if (name == null) throw new IllegalArgumentException("Name cannot be null");
        if (id == null) throw new IllegalArgumentException("Id cannot be null");
        if (deviceID == null)
            throw new IllegalArgumentException("DeviceID cannot be null");
        if (actuatorTypeId == null)
            throw new IllegalArgumentException("ActuatorTypeId cannot be null");
    }
}
