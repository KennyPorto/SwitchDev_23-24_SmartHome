package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.SensorTypeResourceHandler;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.mapper.SensorTypeDTO;
import SmartHome.mapper.SensorTypeMapper;
import SmartHome.service.SensorTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensor-types")
@AllArgsConstructor
public class SensorTypeController {

    private final SensorTypeService sensorTypeService;

    @PostMapping
    public ResponseEntity<SensorTypeDTO> addSensorType(@RequestBody SensorTypeDTO dto) {
        SensorTypeId sensorTypeId = new SensorTypeId(dto.id);
        MeasurementUnit measurementUnit = MeasurementUnit.valueOf(dto.measurementUnit);

        SensorType sensorType = sensorTypeService.add(sensorTypeId, measurementUnit);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SensorTypeResourceHandler.manageAdd(SensorTypeMapper.sensorTypeToDto(sensorType)));
    }

    @GetMapping("/{sensor-type-id}")
    public ResponseEntity<SensorTypeDTO> findSensorTypeById(@PathVariable("sensor-type-id") String id) {
        SensorType sensorType = sensorTypeService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                SensorTypeResourceHandler.manageFindById(SensorTypeMapper.sensorTypeToDto(sensorType)));
    }

    @GetMapping
    public ResponseEntity<Iterable<SensorTypeDTO>> findAllSensorTypes() {
        Iterable<SensorType> sensorTypes = sensorTypeService.findAll();
        Iterable<SensorTypeDTO> sensorTypeDTOs = SensorTypeResourceHandler.manageFindAll(
                SensorTypeMapper.sensorTypesToDto(sensorTypes)
        );

        return ResponseEntity.status(HttpStatus.OK).body(sensorTypeDTOs);
    }
}
