package SmartHome.persistence.springdata;

import SmartHome.domain.repository.SensorTypeRepository;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.mapper.SensorTypeMapper;
import SmartHome.persistence.jpa.datamodel.SensorTypeDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static SmartHome.mapper.SensorTypeMapper.sensorTypeDataModelToDomain;
import static SmartHome.mapper.SensorTypeMapper.sensorTypesDataModelToDomain;

@Repository
public class SensorTypeRepoSpringDataImpl implements SensorTypeRepository
{
    private final SensorTypeRepoSpringDataInterface _sensorTypeRepoSpringDataInterface;

    public SensorTypeRepoSpringDataImpl(SensorTypeRepoSpringDataInterface sensorTypeRepoSpringDataInterface)
    {
        this._sensorTypeRepoSpringDataInterface = sensorTypeRepoSpringDataInterface;
    }
    @Override
    public SensorType save(SensorType entity)
    {
        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(entity);

        SensorTypeDataModel result = _sensorTypeRepoSpringDataInterface.save(sensorTypeDataModel);

        return SensorTypeMapper.sensorTypeDataModelToDomain(result);
    }

    @Override
    public Iterable<SensorType> findAll()
    {
       Iterable<SensorTypeDataModel> sensorTypeDataModels = _sensorTypeRepoSpringDataInterface.findAll();

       return sensorTypesDataModelToDomain(sensorTypeDataModels);
    }

    @Override
    public Optional<SensorType> findById(SensorTypeId id)
    {
        Optional<SensorTypeDataModel> sensorTypeDataModel = _sensorTypeRepoSpringDataInterface.findById(id.id);

        if (sensorTypeDataModel.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(sensorTypeDataModelToDomain(sensorTypeDataModel.get()));
    }

    @Override
    public boolean existsById(SensorTypeId id)
    {
        return _sensorTypeRepoSpringDataInterface.existsById(id.id);
    }
}
