package SmartHome.persistence.jpa.repository;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.repository.ActuatorActivityLogRepository;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.mapper.ActivityLogMapper;
import SmartHome.persistence.jpa.datamodel.ActuatorActivityLogDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ActuatorActivityLogRepositoryImplJPA implements ActuatorActivityLogRepository
{
    @PersistenceContext
    private final EntityManager _entityManager;

    public ActuatorActivityLogRepositoryImplJPA(EntityManager entityManager)
    {
        this._entityManager = entityManager;
    }

    @Override
    public ActuatorActivityLog save(ActuatorActivityLog activityLog)
    {
        ActuatorActivityLogDataModel model = new ActuatorActivityLogDataModel(activityLog);

        ActuatorActivityLogDataModel result = _entityManager.merge(model);

        return ActivityLogMapper.toDomain(result);
    }

    @Override
    public Iterable<ActuatorActivityLog> findAll()
    {
        TypedQuery<ActuatorActivityLogDataModel> query = _entityManager.createQuery(
              "SELECT e FROM ActuatorActivityLogDataModel e", ActuatorActivityLogDataModel.class);

        List<ActuatorActivityLogDataModel> dataModels = query.getResultList();

        return ActivityLogMapper.toDomain(dataModels);
    }

    @Override
    public Optional<ActuatorActivityLog> findById(ActivityLogId id)
    {
        ActuatorActivityLogDataModel model = _entityManager.find(ActuatorActivityLogDataModel.class, id.id);

        if ( model != null ) {
            ActuatorActivityLog activityLog = ActivityLogMapper.toDomain(model);
            return Optional.of(activityLog);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(ActivityLogId id)
    {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<ActuatorActivityLog> findAllByActuatorIdAndTimestampBetween(long actuatorId, ZonedDateTime startDate, ZonedDateTime endDate)
    {
        TypedQuery<ActuatorActivityLogDataModel> query = _entityManager.createQuery(
              "SELECT a FROM ActuatorActivityLogDataModel a WHERE a.actuatorId = :actuatorId AND timeStamp BETWEEN :start AND :end",
              ActuatorActivityLogDataModel.class);
        query.setParameter("actuatorId", actuatorId);
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);

        List<ActuatorActivityLogDataModel> dataModels = query.getResultList();

        return ActivityLogMapper.toDomain(dataModels);
    }

    @Override
    public ActuatorActivityLog findTopByActuatorIdOrderByTimeStampDesc(long actuatorId)
    {
        TypedQuery<ActuatorActivityLogDataModel> query = this._entityManager.createQuery(
              "SELECT a FROM ActuatorActivityLogDataModel " +
                    "a WHERE a.actuatorId = :actuatorId " +
                    "ORDER BY a.timeStamp DESC",
              ActuatorActivityLogDataModel.class);
        query.setParameter("actuatorId", actuatorId);
        query.setMaxResults(1);

        ActuatorActivityLogDataModel lastEntry = query.getSingleResult();
        if ( lastEntry != null )
            return ActivityLogMapper.toDomain(lastEntry);
        return null;
    }


    @Override
    public Iterable<ActuatorActivityLog> findAllByActuatorId(long actuatorId)
    {
        TypedQuery<ActuatorActivityLogDataModel> query = _entityManager.createQuery(
                "SELECT a FROM ActuatorActivityLogDataModel a WHERE a.actuatorId = :actuatorId",
                ActuatorActivityLogDataModel.class);
        query.setParameter("actuatorId", actuatorId);

        List<ActuatorActivityLogDataModel> dataModels = query.getResultList();

        return ActivityLogMapper.toDomain(dataModels);
    }
}
