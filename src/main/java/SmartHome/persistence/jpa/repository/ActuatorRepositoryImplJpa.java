package SmartHome.persistence.jpa.repository;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.mapper.ActuatorMapper;
import SmartHome.persistence.jpa.datamodel.ActuatorDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional

public class ActuatorRepositoryImplJpa implements ActuatorRepository
{
    @PersistenceContext
    private final EntityManager _entityManager;

    public ActuatorRepositoryImplJpa(EntityManager entityManager)
    {
        this._entityManager = entityManager;
    }

    @Override
    public Actuator save(Actuator actuator)
    {
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);

        ActuatorDataModel result = _entityManager.merge(actuatorDataModel);

        return ActuatorMapper.dataModelToDomain(result);
    }

    @Override
    public Iterable<Actuator> findAllByDeviceId(Long deviceId)
    {
        TypedQuery<ActuatorDataModel> query = _entityManager.createQuery(
                "SELECT e FROM ActuatorDataModel e WHERE e.deviceId = :deviceId", ActuatorDataModel.class);
        query.setParameter("deviceId", deviceId);

        List<ActuatorDataModel> listDataModel = query.getResultList();

        return ActuatorMapper.dataModelListToDomain(listDataModel);
    }

    @Override
    public Iterable<Actuator> findAll()
    {
        TypedQuery<ActuatorDataModel> query = _entityManager.createQuery(
                "SELECT e FROM ActuatorDataModel e", ActuatorDataModel.class);

        List<ActuatorDataModel> listDataModel = query.getResultList();

        return ActuatorMapper.dataModelListToDomain(listDataModel);
    }

    @Override
    public Optional<Actuator> findById(ActuatorId id)
    {
        ActuatorDataModel actuatorDataModel = _entityManager.find(ActuatorDataModel.class, id.id);

        if (actuatorDataModel != null) {
            Actuator actuatorDomain = ActuatorMapper.dataModelToDomain(actuatorDataModel);
            return Optional.of(actuatorDomain);
        } else
            return Optional.empty();
    }

    @Override
    public boolean existsById(ActuatorId id)
    {
        Optional<Actuator> optionalActuator = findById(id);

        return optionalActuator.isPresent();
    }
}
