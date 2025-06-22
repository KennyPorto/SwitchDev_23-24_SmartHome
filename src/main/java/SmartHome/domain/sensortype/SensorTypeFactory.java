package SmartHome.domain.sensortype;

import SmartHome.domain.valueObjects.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorTypeFactory {
    public SensorType createSensorType(SensorTypeId sensorTypeId,
                                       MeasurementUnit measurementUnit) {
        return new SensorType(sensorTypeId, measurementUnit);
    }

    public Optional<SensorTypeId> createSensorTypeName(String name) {
        try {
            return Optional.of(new SensorTypeId(name));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
