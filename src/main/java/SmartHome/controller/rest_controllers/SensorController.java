package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.SensorResourceHandler;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.SensorDTO;
import SmartHome.mapper.SensorMapper;
import SmartHome.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
@AllArgsConstructor
public class SensorController
{
    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<Object> addSensor(@RequestBody SensorDTO dto)
    {
        SensorModel sensorModel = new SensorModel(dto.model);
        DeviceID deviceId = new DeviceID(dto.deviceId);
        SensorTypeId sensorTypeId = new SensorTypeId(dto.sensorType);
        SensorName sensorName = new SensorName(dto.name);
        SensorId sensorId = new SensorId(dto.sensorId);

        Sensor sensor = sensorService.add(sensorId, sensorModel, deviceId, sensorTypeId, sensorName);
        return ResponseEntity.status(HttpStatus.CREATED).body(
              SensorResourceHandler.manageAdd(
                    SensorMapper.toDTO(sensor)
              ));
    }

    @GetMapping("/{sensor-id}")
    public ResponseEntity<SensorDTO> findSensorById(@PathVariable("sensor-id") long id)
    {
        Sensor sensor = sensorService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(SensorResourceHandler.manageFindById(
              SensorMapper.toDTO(sensor)));
    }

    @GetMapping
    public ResponseEntity<Iterable<SensorDTO>> findAllSensors()
    {
        Iterable<Sensor> sensors = sensorService.findAll();
        Iterable<SensorDTO> sensorDTOs = SensorResourceHandler.manageFindAll(
              SensorMapper.toDTO(sensors)
        );

        return ResponseEntity.status(HttpStatus.OK).body(sensorDTOs);
    }
    @GetMapping(params = "device-id")
    public ResponseEntity<Iterable<SensorDTO>> findAllSensorsByDevice(@RequestParam("device-id") long deviceId)
    {
        Iterable<Sensor> sensors = sensorService.findAllByDeviceId(deviceId);

        Iterable<SensorDTO> sensorDTOs = SensorResourceHandler.manageFindAll(
                SensorMapper.toDTO(sensors)
        );
        return ResponseEntity.status(HttpStatus.OK).body(sensorDTOs);
    }
}
