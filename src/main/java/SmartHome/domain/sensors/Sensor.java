package SmartHome.domain.sensors;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.*;
import SmartHome.utils.Validations;
import lombok.Getter;

@Getter
public abstract class Sensor implements AggregateRoot<SensorId> {
    private final SensorName name;
    private final SensorId sensorID;
    private final DeviceID deviceID;
    private final SensorTypeId sensorTypeId;
    private final SensorModel sensorModel;

    public Sensor(SensorName name, SensorId sensorID, DeviceID deviceID, SensorTypeId sensorTypeId,
                  SensorModel sensorModel) {
        new Validations().getSensorArgsValidation(deviceID, name, sensorID, sensorTypeId);
        this.name = name;
        this.sensorID = sensorID;
        this.deviceID = deviceID;
        this.sensorTypeId = sensorTypeId;
        this.sensorModel = sensorModel;
    }

    public SensorId identity()
    {
        return this.sensorID;
    }
}