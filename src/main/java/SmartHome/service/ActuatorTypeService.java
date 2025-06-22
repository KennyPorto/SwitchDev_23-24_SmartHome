package SmartHome.service;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.repository.ActuatorTypeRepository;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.domain.valueObjects.MeasurementUnit;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActuatorTypeService {

    private final ActuatorTypeRepository _repository;

    private final ActuatorTypeFactory _actuatorTypeFactory;

    public ActuatorTypeService(
            @Qualifier("actuatorTypeRepoSpringDataImpl") ActuatorTypeRepository repository,
            ActuatorTypeFactory actuatorTypeFactory)
    {
        this._repository = repository;
        this._actuatorTypeFactory = actuatorTypeFactory;
    }

    public ActuatorType add(ActuatorTypeId actuatorTypeId, MeasurementUnit measurementUnit)
    {
        if (this._repository.existsById(actuatorTypeId))
        {
            throw new IllegalArgumentException("Actuator type id already exists");
        }

        ActuatorType savedActuatorType = _actuatorTypeFactory.createActuatorType(actuatorTypeId, measurementUnit);
        if (savedActuatorType == null)
        {
            throw new IllegalArgumentException("Invalid actuator type attributes");
        }

        return _repository.save(savedActuatorType);
    }

    public Iterable<ActuatorType> findAll()
    {
        return _repository.findAll();
    }

    public ActuatorType findById(String id)
    {
        Optional<ActuatorTypeId> actuatorTypeId = _actuatorTypeFactory.createActuatorTypeName(id);
        if (actuatorTypeId.isEmpty())
        {
            throw new IllegalArgumentException("Bad id input");
        }

        Optional<ActuatorType> actuatorType = _repository.findById(actuatorTypeId.get());

        if (actuatorType.isEmpty())
        {
            throw new IllegalArgumentException("Not found by id.");
        }

        return actuatorType.get();
    }

    public Pair<Boolean, ActuatorTypeId> existsById(String id)
    {
        Optional<ActuatorTypeId> actuatorTypeId = _actuatorTypeFactory.createActuatorTypeName(id);
        if (actuatorTypeId.isEmpty())
        {
            throw new IllegalArgumentException("Bad id input");
        }

        return Pair.of(_repository.existsById(actuatorTypeId.get()), actuatorTypeId.get());
    }
}
