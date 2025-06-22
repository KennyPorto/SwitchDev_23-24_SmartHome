package SmartHome.persistence.springdata;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.repository.ActuatorTypeRepository;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.mapper.ActuatorTypeMapper;
import SmartHome.persistence.jpa.datamodel.ActuatorTypeDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ActuatorTypeRepoSpringDataImpl implements ActuatorTypeRepository
{
    private final ActuatorTypeRepoSpringDataInterface _actuatorTypeRepoSpringDataInterface;

    public ActuatorTypeRepoSpringDataImpl(ActuatorTypeRepoSpringDataInterface actuatorTypeRepoSpringDataInterface) {
        this._actuatorTypeRepoSpringDataInterface = actuatorTypeRepoSpringDataInterface;
    }

    @Override
    public ActuatorType save(ActuatorType entity) {
        ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(entity);

        ActuatorTypeDataModel result = _actuatorTypeRepoSpringDataInterface.save(actuatorTypeDataModel);

        return ActuatorTypeMapper.toDomain(result);
    }

    @Override
    public Iterable<ActuatorType> findAll() {
        Iterable<ActuatorTypeDataModel> actuatorTypeDataModels = _actuatorTypeRepoSpringDataInterface.findAll();

        return ActuatorTypeMapper.toDomain(actuatorTypeDataModels);
    }

    @Override
    public Optional<ActuatorType> findById(ActuatorTypeId id) {
        Optional<ActuatorTypeDataModel> actuatorTypeDataModel = _actuatorTypeRepoSpringDataInterface.findById(id.name);

        return actuatorTypeDataModel.map(ActuatorTypeMapper::toDomain);
    }

    @Override
    public boolean existsById(ActuatorTypeId id) {
        return _actuatorTypeRepoSpringDataInterface.existsById(id.name);
    }
}
