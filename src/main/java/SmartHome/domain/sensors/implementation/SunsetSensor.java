package SmartHome.domain.sensors.implementation;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

import java.util.Objects;

public class SunsetSensor extends Sensor
{

    public SunsetSensor(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                        SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    @Override
    public boolean sameAs(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SunsetSensor that = (SunsetSensor) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getSensorID(), that.getSensorID())
                && Objects.equals(getDeviceID(), that.getDeviceID())
                && Objects.equals(getSensorTypeId(), that.getSensorTypeId());
    }
}


