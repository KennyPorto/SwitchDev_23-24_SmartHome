package SmartHome.service;

import SmartHome.domain.repository.SensorTypeRepository;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorTypeService {
    private final SensorTypeRepository _repository;
    private final SensorTypeFactory _sensorTypeFactory;

    public SensorTypeService(
            @Qualifier("sensorTypeRepoSpringDataImpl") SensorTypeRepository repository,
            SensorTypeFactory sensorTypeFactory) {
        this._repository = repository;
        this._sensorTypeFactory = sensorTypeFactory;
    }

    public SensorType add(SensorTypeId sensorTypeId, MeasurementUnit measurementUnit) {
        if (this._repository.existsById(sensorTypeId)) {
            throw new IllegalArgumentException("Sensor type id already exists");
        }

        SensorType savedSensorType = _sensorTypeFactory.createSensorType(sensorTypeId, measurementUnit);
        if (savedSensorType == null) {
            throw new IllegalArgumentException("Invalid sensor type attributes");
        }

        return _repository.save(savedSensorType);
    }

    public Iterable<SensorType> findAll() {
        return _repository.findAll();
    }

    public SensorType findById(String id) {
        Optional<SensorTypeId> sensorTypeId = _sensorTypeFactory.createSensorTypeName(id);
        if (sensorTypeId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<SensorType> sensorType = _repository.findById(sensorTypeId.get());
        if (sensorType.isEmpty()) {
            throw new IllegalArgumentException("Not found by id.");
        }

        return sensorType.get();
    }

    public Pair<Boolean, SensorTypeId> existsById(String id) {
        Optional<SensorTypeId> sensorTypeId = _sensorTypeFactory.createSensorTypeName(id);
        if (sensorTypeId.isEmpty()) {
            throw new IllegalArgumentException("Bad id input");
        }

        return Pair.of(_repository.existsById(sensorTypeId.get()), sensorTypeId.get());
    }
}
