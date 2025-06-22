package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.ActuatorResourceHandler;
import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.implementation.PercentageValue;
import SmartHome.mapper.ActuatorDTO;
import SmartHome.mapper.ActuatorMapper;
import SmartHome.service.ActuatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static SmartHome.utils.Constants.RANGE_DECIMAL;
import static SmartHome.utils.Constants.RANGE_INT;

@RestController
@RequestMapping("/api/v1/actuators")
@AllArgsConstructor
public class ActuatorController
{
    private final ActuatorService actuatorService;

    @PostMapping
    public ResponseEntity<ActuatorDTO> addActuator(@RequestBody ActuatorDTO dto)
    {
        DeviceID deviceID = new DeviceID(dto.deviceId);
        ActuatorModel actuatorModel = new ActuatorModel(dto.model);
        ActuatorName name = new ActuatorName(dto.name);
        ActuatorId id = new ActuatorId(dto.actuatorId);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(dto.actuatorType);
        LimitInt limitInt = new LimitInt(0, 1);
        LimitFractional limitFractional = new LimitFractional(0.0, 1.0);
        PercentageValue percentageValue = new PercentageValue(dto.currentValue);

        if ( dto.model.equals(RANGE_INT) ) {
            limitInt = new LimitInt(dto.lowerLimit, dto.upperLimit);
        }
        if ( dto.model.equals(RANGE_DECIMAL) ) {
            limitFractional = new LimitFractional(dto.lowerLimitFractional, dto.upperLimitFractional);
        }

        Actuator actuator = actuatorService.add(deviceID, actuatorModel, name, id, actuatorTypeId,
              limitInt, limitFractional, percentageValue);

        return ResponseEntity.status(HttpStatus.CREATED).body(
              ActuatorResourceHandler.manageAdd(
                    ActuatorMapper.toDTO(actuator)
              ));
    }

    @GetMapping("/{actuator-id}")
    public ResponseEntity<ActuatorDTO> findActuatorById(@PathVariable("actuator-id") long id)
    {
        Actuator actuator = actuatorService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
              ActuatorResourceHandler.manageFindById(
                    ActuatorMapper.toDTO(actuator))
        );
    }

    @GetMapping
    public ResponseEntity<Iterable<ActuatorDTO>> findAllActuators()
    {
        Iterable<Actuator> actuators = actuatorService.findAll();
        Iterable<ActuatorDTO> actuatorDTOs = ActuatorResourceHandler.manageFindAll(
              ActuatorMapper.toDTO(actuators)
        );

        return ResponseEntity.status(HttpStatus.OK).body(actuatorDTOs);
    }
    @GetMapping(params = "device-id")
    public ResponseEntity<Iterable<ActuatorDTO>> findAllActuatorsByDevice(@RequestParam("device-id") long deviceId)
    {
        Iterable<Actuator> actuators = actuatorService.findAllByDeviceId(deviceId);

        Iterable<ActuatorDTO> actuatorDTOs = ActuatorResourceHandler.manageFindAll(
                ActuatorMapper.toDTO(actuators)
        );
        return ResponseEntity.status(HttpStatus.OK).body(actuatorDTOs);
    }
}
