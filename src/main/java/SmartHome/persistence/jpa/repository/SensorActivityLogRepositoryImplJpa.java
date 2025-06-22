package SmartHome.persistence.jpa.repository;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.repository.SensorActivityLogRepository;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.mapper.ActivityLogMapper;
import SmartHome.persistence.jpa.datamodel.SensorActivityLogDataModel;
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
public class SensorActivityLogRepositoryImplJpa implements SensorActivityLogRepository {
    @PersistenceContext
    private final EntityManager _entityManager;

    public SensorActivityLogRepositoryImplJpa(EntityManager entityManager) {
        this._entityManager = entityManager;
    }

    @Override
    public SensorActivityLog save(SensorActivityLog activityLog) {
        SensorActivityLogDataModel model = new SensorActivityLogDataModel(activityLog);

        SensorActivityLogDataModel result = _entityManager.merge(model);

        return ActivityLogMapper.sensorDataModelToDomain(result);
    }


    @Override
    public Iterable<SensorActivityLog> findAll() {
        TypedQuery<SensorActivityLogDataModel> query = _entityManager.createQuery(
                "SELECT e FROM SensorActivityLogDataModel e", SensorActivityLogDataModel.class);

        List<SensorActivityLogDataModel> dataModels = query.getResultList();

        return ActivityLogMapper.sensorsDataModelToDomain(dataModels);
    }

    @Override
    public Optional<SensorActivityLog> findById(ActivityLogId id) {
        SensorActivityLogDataModel model = _entityManager.find(SensorActivityLogDataModel.class, id.id);

        if (model != null) {
            SensorActivityLog activityLog = ActivityLogMapper.sensorDataModelToDomain(model);
            return Optional.of(activityLog);
        }
        return Optional.empty();
    }


    @Override
    public boolean existsById(ActivityLogId id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<SensorActivityLog> findAllBySensorIdAndTimestampBetween(long sensorId, ZonedDateTime startDate, ZonedDateTime endDate) {
        TypedQuery<SensorActivityLogDataModel> query = _entityManager.createQuery(
                "SELECT a FROM SensorActivityLogDataModel a WHERE a.sensorId = :sensorId AND timeStamp BETWEEN :start AND :end",
                SensorActivityLogDataModel.class);
        query.setParameter("sensorId", sensorId);
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);

        List<SensorActivityLogDataModel> dataModels = query.getResultList();

        return ActivityLogMapper.sensorsDataModelToDomain(dataModels);
    }

    @Override
    public Iterable<SensorActivityLog> findAllBySensorId(long sensorId) {
        TypedQuery<SensorActivityLogDataModel> query = _entityManager.createQuery(
                "SELECT a FROM SensorActivityLogDataModel a WHERE a.sensorId = :sensorId",
                SensorActivityLogDataModel.class);
        query.setParameter("sensorId", sensorId);

        List<SensorActivityLogDataModel> dataModels = query.getResultList();

        return ActivityLogMapper.sensorsDataModelToDomain(dataModels);
    }
}
