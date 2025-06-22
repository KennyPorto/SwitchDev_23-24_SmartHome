package SmartHome.domain.sensors.implementation;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

import java.util.Objects;

public class SunriseSensor extends Sensor
{
    public SunriseSensor(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                         SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    @Override
    public boolean sameAs(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SunriseSensor that = (SunriseSensor) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getDeviceID(), that.getDeviceID())
                && Objects.equals(getSensorID(), that.getSensorID())
                && Objects.equals(getSensorTypeId(), that.getSensorTypeId());
    }
}


