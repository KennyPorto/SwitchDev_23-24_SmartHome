package SmartHome.domain.actuatortype;

import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActuatorTypeFactory {
    public ActuatorType createActuatorType(ActuatorTypeId actuatorTypeId,
                                           MeasurementUnit measurementUnit) {
        return new ActuatorType(actuatorTypeId, measurementUnit);
    }

    public Optional<ActuatorTypeId> createActuatorTypeName(String name) {
        try {
            return Optional.of(new ActuatorTypeId(name));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
