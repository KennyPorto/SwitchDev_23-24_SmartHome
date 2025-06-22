package SmartHome.controller.cli_controllers;

import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.mapper.SensorTypeDTO;
import SmartHome.mapper.SensorTypeMapper;
import SmartHome.service.SensorTypeService;

public class Ctrl04AddSensorType
{
    private final SensorTypeService _sensorTypeService;

    public Ctrl04AddSensorType(SensorTypeService sensorTypeService) {
        this._sensorTypeService = sensorTypeService;
    }

    public Iterable<SensorTypeDTO> getAllSensorTypes() {
        Iterable<SensorType> sensorTypes = _sensorTypeService.findAll();
        return SensorTypeMapper.sensorTypesToDto(sensorTypes);
    }

    public SensorTypeDTO addSensorType(SensorTypeId name, MeasurementUnit measurementUnit) {
        SensorType sensorType = _sensorTypeService.add(name, measurementUnit);
        if (sensorType == null) return null;
        return SensorTypeMapper.sensorTypeToDto(sensorType);
    }
}
