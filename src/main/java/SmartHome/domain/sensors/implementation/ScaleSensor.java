package SmartHome.domain.sensors.implementation;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

import java.util.Objects;

public class ScaleSensor extends Sensor
{

    public ScaleSensor(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                       SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    @Override
    public boolean sameAs(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaleSensor that = (ScaleSensor) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getDeviceID(), that.getDeviceID())
                && Objects.equals(getSensorID(), that.getSensorID())
                && Objects.equals(getSensorTypeId(), that.getSensorTypeId());
    }
}

