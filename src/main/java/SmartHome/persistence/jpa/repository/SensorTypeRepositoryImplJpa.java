package SmartHome.persistence.jpa.repository;

import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.mapper.SensorTypeMapper;
import SmartHome.persistence.jpa.datamodel.SensorTypeDataModel;
import SmartHome.domain.repository.SensorTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SensorTypeRepositoryImplJpa implements SensorTypeRepository {
    @PersistenceContext
    EntityManager _entityManager;


    public SensorTypeRepositoryImplJpa(EntityManager entityManager) {
        this._entityManager = entityManager;
    }


    @Override
    public SensorType save(SensorType entity) {
        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(entity);

        SensorTypeDataModel result = _entityManager.merge(sensorTypeDataModel);

        return SensorTypeMapper.sensorTypeDataModelToDomain(result);
    }


    @Override
    public Iterable<SensorType> findAll() {
        TypedQuery<SensorTypeDataModel> query = _entityManager.createQuery(
                "SELECT e FROM SensorTypeDataModel e", SensorTypeDataModel.class);

        List<SensorTypeDataModel> listDataModel = query.getResultList();

        return SensorTypeMapper.sensorTypesDataModelToDomain(listDataModel);
    }


    @Override
    public Optional<SensorType> findById(SensorTypeId id) {
        SensorTypeDataModel sensorTypeDataModel = _entityManager.find(SensorTypeDataModel.class, id.toString());

        if (sensorTypeDataModel == null) return Optional.empty();

        SensorType sensorType = SensorTypeMapper.sensorTypeDataModelToDomain(sensorTypeDataModel);

        return Optional.of(sensorType);
    }


    @Override
    public boolean existsById(SensorTypeId id) {
        Optional<SensorType> optionalSensorType = findById(id);

        return optionalSensorType.isPresent();
    }

}
