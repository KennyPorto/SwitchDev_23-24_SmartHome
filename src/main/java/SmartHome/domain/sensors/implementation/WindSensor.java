package SmartHome.domain.sensors.implementation;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

import java.util.Objects;

public class WindSensor extends Sensor
{

    public WindSensor(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                      SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    @Override
    public boolean sameAs(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WindSensor that = (WindSensor) object;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getSensorID(), that.getSensorID())
                && Objects.equals(getDeviceID(), that.getDeviceID())
                && Objects.equals(getSensorTypeId(), that.getSensorTypeId());
    }
}