package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.SensorActivityLogResourceHandler;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.SensorId;
import SmartHome.domain.valueObjects.TimeStamp;
import SmartHome.mapper.ActivityLogMapper;
import SmartHome.mapper.SensorActivityLogDTO;
import SmartHome.service.SensorActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/sensor-activity-logs")
@AllArgsConstructor
public class SensorActivityLogController {
    private final SensorActivityLogService sensorActivityLogService;

    @PostMapping
    public ResponseEntity<SensorActivityLogDTO> addSensorActivityLog(@RequestBody SensorActivityLogDTO dto) {
        ActivityLogId activityLogId = new ActivityLogId(dto.sensorActivityLogId);
        SensorId sensorId = new SensorId(dto.sensorId);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse(dto.timeStamp));
        Measurement measurement = new Measurement(dto.measurement);

        SensorActivityLog sensorActivityLog =
                sensorActivityLogService.add(sensorId, timeStamp, activityLogId, measurement);

        return ResponseEntity.status(HttpStatus.CREATED).body(SensorActivityLogResourceHandler.manageAdd(
                ActivityLogMapper.sensorToDTO(sensorActivityLog)));
    }

    @GetMapping("/{sensor-log-id}")
    public ResponseEntity<SensorActivityLogDTO> findSensorActivityLogById(@PathVariable("sensor-log-id") long id) {
        SensorActivityLog sensorActivityLog = sensorActivityLogService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(SensorActivityLogResourceHandler.manageFindById(
                ActivityLogMapper.sensorToDTO(sensorActivityLog)));
    }

    @GetMapping(params = "sensor-id")
    public ResponseEntity<Iterable<SensorActivityLogDTO>> findAllSensorActivityLogsBySensorId(@RequestParam("sensor-id") long sensorId)
    {
        Iterable<SensorActivityLog> sensorActivityLogs = sensorActivityLogService.findAllBySensorId(sensorId);
        Iterable<SensorActivityLogDTO> sensorActivityLogsDTO = SensorActivityLogResourceHandler.manageFindAll(
                ActivityLogMapper.sensorsToDTO(sensorActivityLogs)
        );
        return ResponseEntity.status(HttpStatus.OK).body(sensorActivityLogsDTO);
    }

    @GetMapping
    public ResponseEntity<Iterable<SensorActivityLogDTO>> findAllSensorActivityLogs() {
        Iterable<SensorActivityLog> sensorActivityLogs = sensorActivityLogService.findAll();
        Iterable<SensorActivityLogDTO> sensorActivityLogDTOs = SensorActivityLogResourceHandler.manageFindAll(
                ActivityLogMapper.sensorLogAndMeasurementToDto(sensorActivityLogs)
        );

        return ResponseEntity.status(HttpStatus.OK).body(sensorActivityLogDTOs);
    }
}
