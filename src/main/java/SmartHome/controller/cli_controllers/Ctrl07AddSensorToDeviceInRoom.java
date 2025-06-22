package SmartHome.controller.cli_controllers;

import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.SensorDTO;
import SmartHome.service.SensorService;

public class Ctrl07AddSensorToDeviceInRoom
{
    private final SensorService sensorService;

    public Ctrl07AddSensorToDeviceInRoom(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public boolean addSensor(SensorDTO sensorDTO) {
        SensorId sensorId = new SensorId(sensorDTO.sensorId);
        SensorModel sensorModel = new SensorModel(sensorDTO.model);
        DeviceID deviceId = new DeviceID(sensorDTO.deviceId);
        SensorTypeId sensorTypeId = new SensorTypeId(sensorDTO.sensorType);
        SensorName sensorName = new SensorName(sensorDTO.name);

        return sensorService.add(sensorId, sensorModel, deviceId, sensorTypeId, sensorName) != null;
    }
}
