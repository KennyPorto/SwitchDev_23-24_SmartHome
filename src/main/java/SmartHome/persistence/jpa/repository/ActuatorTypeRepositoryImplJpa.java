package SmartHome.persistence.jpa.repository;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.repository.ActuatorTypeRepository;
import SmartHome.domain.valueObjects.ActuatorTypeId;
import SmartHome.mapper.ActuatorTypeMapper;
import SmartHome.persistence.jpa.datamodel.ActuatorTypeDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class ActuatorTypeRepositoryImplJpa implements ActuatorTypeRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public ActuatorTypeRepositoryImplJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ActuatorType save(ActuatorType actuatorType) {
        ActuatorTypeDataModel dataModel = new ActuatorTypeDataModel(actuatorType);

        ActuatorTypeDataModel result = entityManager.merge(dataModel);

        return ActuatorTypeMapper.toDomain(result);
    }

    @Override
    public Iterable<ActuatorType> findAll() {
        TypedQuery<ActuatorTypeDataModel> query = entityManager.createQuery(
                "SELECT a FROM ActuatorTypeDataModel a", ActuatorTypeDataModel.class
        );

        return ActuatorTypeMapper.toDomain(query.getResultList());
    }

    @Override
    public Optional<ActuatorType> findById(ActuatorTypeId id) {
        ActuatorTypeDataModel actuatorType = entityManager.find(ActuatorTypeDataModel.class, id.name);
        if (actuatorType == null)
            return Optional.empty();
        return Optional.of(ActuatorTypeMapper.toDomain(actuatorType));
    }

    @Override
    public boolean existsById(ActuatorTypeId id) {
        Optional<ActuatorType> actuatorType = findById(id);
        return actuatorType.isPresent();
    }

}
