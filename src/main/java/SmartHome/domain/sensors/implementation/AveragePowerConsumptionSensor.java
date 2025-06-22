package SmartHome.domain.sensors.implementation;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

public class AveragePowerConsumptionSensor extends Sensor
{

    public AveragePowerConsumptionSensor(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                                         SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    @Override
    public boolean sameAs(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AveragePowerConsumptionSensor that = (AveragePowerConsumptionSensor) object;

        if (!getName().equals(that.getName())) return false;
        if (!getDeviceID().equals(that.getDeviceID())) return false;
        if (!getSensorID().equals(that.getSensorID())) return false;
        return getSensorTypeId().equals(that.getSensorTypeId());
    }
}
