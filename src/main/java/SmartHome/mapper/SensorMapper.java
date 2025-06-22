package SmartHome.mapper;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.SensorDataModel;

import java.util.ArrayList;
import java.util.List;

public class SensorMapper {
    public static SensorDTO toDTO(Sensor sensor) {
        return new SensorDTO(
              sensor.getName().name,
              sensor.getSensorModel().model,
              sensor.identity().id,
              sensor.getDeviceID().id,
              sensor.getSensorTypeId().id
        );
    }

    public static Iterable<SensorDTO> toDTO(Iterable<Sensor> sensors) {
        List<SensorDTO> sensorDTOS = new ArrayList<>();
        sensors.forEach(
              sensor -> sensorDTOS.add(
                    toDTO(sensor)
              ));
        return sensorDTOS;
    }

    public static Sensor dataModelToDomain(SensorDataModel dataModel)
    {
        SensorId id = new SensorId(dataModel.getId());
        SensorName name = new SensorName(dataModel.getSensorName());
        SensorTypeId sensorTypeId = new SensorTypeId(dataModel.getSensorTypeId());
        DeviceID deviceId = new DeviceID(dataModel.getDeviceId());
        SensorModel sensorModel = new SensorModel(dataModel.getSensorModel());
        return new SensorFactory().createSensor(deviceId, sensorModel, name, id, sensorTypeId);
    }

    public static Iterable<Sensor> dataModelListToDomain(Iterable<SensorDataModel> dataModels)
    {
        List<Sensor> sensors = new ArrayList<>();
        dataModels.forEach(
                dataModel -> sensors.add(dataModelToDomain(dataModel))
        );
        return sensors;
    }
}