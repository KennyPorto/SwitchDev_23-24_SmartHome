package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.ActuatorTypeResourceHandler;
import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.mapper.ActuatorTypeDTO;
import SmartHome.mapper.ActuatorTypeMapper;
import SmartHome.service.ActuatorTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/actuator-types")
@AllArgsConstructor
public class ActuatorTypeController {
    private final ActuatorTypeService actuatorTypeService;

    @PostMapping
    public ResponseEntity<ActuatorTypeDTO> addActuatorType(@RequestBody ActuatorTypeDTO dto) {
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId(dto.name);
        MeasurementUnit measurementUnit = MeasurementUnit.valueOf(dto.measurementUnit);

        ActuatorType actuatorType = actuatorTypeService.add(actuatorTypeId, measurementUnit);

        return ResponseEntity.status(HttpStatus.CREATED).body(ActuatorTypeResourceHandler.manageAdd(
                            ActuatorTypeMapper.actuatorTypeToDTO(actuatorType)));
    }

    @GetMapping("/{actuator-type-id}")
    public ResponseEntity<ActuatorTypeDTO> findActuatorTypeById(@PathVariable("actuator-type-id") String id) {
        ActuatorType actuatorType = actuatorTypeService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ActuatorTypeResourceHandler.manageFindById(
                            ActuatorTypeMapper.actuatorTypeToDTO(actuatorType)));
    }

    @GetMapping
    public ResponseEntity<Iterable<ActuatorTypeDTO>> findAllActuatorTypes() {
        Iterable<ActuatorType> actuatorTypes = actuatorTypeService.findAll();
        Iterable<ActuatorTypeDTO> actuatorTypeDTOs = ActuatorTypeResourceHandler.manageFindAll(
                ActuatorTypeMapper.actuatorTypesToDTO(actuatorTypes)
        );

        return ResponseEntity.status(HttpStatus.OK).body(actuatorTypeDTOs);
    }
}
