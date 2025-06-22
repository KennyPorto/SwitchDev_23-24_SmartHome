package SmartHome.domain.sensors.implementation;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

import java.util.Objects;

public class HumiditySensor extends Sensor
{
    public HumiditySensor(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                          SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    @Override
    public boolean sameAs(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumiditySensor that = (HumiditySensor) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getSensorID(), that.getSensorID())
                && Objects.equals(getSensorTypeId(), that.getSensorTypeId())
                && Objects.equals(getDeviceID(), that.getDeviceID());
    }
}
