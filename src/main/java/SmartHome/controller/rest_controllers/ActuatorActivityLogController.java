package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.ActuatorActivityLogResourceHandler;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;
import SmartHome.mapper.ActivityLogMapper;
import SmartHome.mapper.ActuatorActivityLogDTO;
import SmartHome.service.ActuatorActivityLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/actuator-activity-logs")
@AllArgsConstructor
public class ActuatorActivityLogController
{
    private final ActuatorActivityLogService actuatorActivityLogService;

    @PostMapping
    public ResponseEntity<ActuatorActivityLogDTO> addActuatorActivityLog(@RequestBody ActuatorActivityLogDTO dto)
    {
        ActivityLogId activityLogId = new ActivityLogId(dto.actuatorActivityLogId);
        ActuatorId actuatorId = new ActuatorId(dto.actuatorId);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse(dto.timeStamp));
        Measurement measurement = new Measurement(dto.measurement);

        ActuatorActivityLog actuatorActivityLog =
                actuatorActivityLogService.add(actuatorId, timeStamp, activityLogId, measurement);

        return ResponseEntity.status(HttpStatus.CREATED).body(ActuatorActivityLogResourceHandler.manageAdd(
                            ActivityLogMapper.toDTO(actuatorActivityLog)));
    }

    @GetMapping("/{actuator-log-id}")
    public ResponseEntity<ActuatorActivityLogDTO> findActuatorActivityLogById(@PathVariable("actuator-log-id") long id)
    {
        ActuatorActivityLog actuatorActivityLog = actuatorActivityLogService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ActuatorActivityLogResourceHandler.manageFindById(
                            ActivityLogMapper.toDTO(actuatorActivityLog)));
    }

    @GetMapping
    public ResponseEntity<Iterable<ActuatorActivityLogDTO>> findAllActuatorActivityLogs()
    {
        Iterable<ActuatorActivityLog> actuatorActivityLogs = actuatorActivityLogService.findAll();
        Iterable<ActuatorActivityLogDTO> actuatorActivityLogDTOs = ActuatorActivityLogResourceHandler.manageFindAll(
                ActivityLogMapper.actuatorLogAndMeasurementToDto(actuatorActivityLogs)
        );

        return ResponseEntity.status(HttpStatus.OK).body(actuatorActivityLogDTOs);
    }

    @GetMapping(params = "actuator-id")
    public ResponseEntity<Iterable<ActuatorActivityLogDTO>> findAllActuatorActivityLogsBySensorId(
            @RequestParam("actuator-id") long actuatorId)
    {
        Iterable<ActuatorActivityLog> actuatorActivityLogs = actuatorActivityLogService.findAllByActuatorId(actuatorId);
        Iterable<ActuatorActivityLogDTO> actuatorActivityLogsDTO = ActuatorActivityLogResourceHandler.manageFindAll(
                ActivityLogMapper.toDTOs(actuatorActivityLogs)
        );
        return ResponseEntity.status(HttpStatus.OK).body(actuatorActivityLogsDTO);
    }

}
