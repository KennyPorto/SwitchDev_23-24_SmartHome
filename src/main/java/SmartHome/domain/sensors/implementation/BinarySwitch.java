package SmartHome.domain.sensors.implementation;

import SmartHome.domain.actuators.implementation.SwitchOnOffActuator;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;

import java.util.Objects;

public class BinarySwitch extends Sensor
{
    private SwitchOnOffActuator _binarySwitch;

    public BinarySwitch(DeviceID deviceID, SensorName name, SensorId sensorID, SensorTypeId sensorTypeId,
                        SensorModel sensorModel) {
        super(name, sensorID, deviceID, sensorTypeId, sensorModel);
    }

    public boolean configureSensor(SwitchOnOffActuator actuator)
    {
        if (actuator == null) return false;
        this._binarySwitch = actuator;
        return true;
    }

    public boolean readStatus()
    {
        return this._binarySwitch.isOn();
    }

    @Override
    public boolean sameAs(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        BinarySwitch that = (BinarySwitch) object;

        if (!getName().equals(that.getName())) return false;
        if (!getSensorID().equals(that.getSensorID())) return false;
        if (!getDeviceID().equals(that.getDeviceID())) return false;
        if (getSensorTypeId() != that.getSensorTypeId()) return false;
        return Objects.equals(_binarySwitch, that._binarySwitch);
    }
}