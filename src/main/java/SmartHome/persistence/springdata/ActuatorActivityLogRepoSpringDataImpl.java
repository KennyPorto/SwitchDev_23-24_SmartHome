package SmartHome.persistence.springdata;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.repository.ActuatorActivityLogRepository;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.mapper.ActivityLogMapper;
import SmartHome.persistence.jpa.datamodel.ActuatorActivityLogDataModel;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public class ActuatorActivityLogRepoSpringDataImpl implements ActuatorActivityLogRepository
{
    private final ActuatorActivityLogRepoSpringDataInterface actuatorActivityLogRepoSpringDataInterface;

    public ActuatorActivityLogRepoSpringDataImpl(ActuatorActivityLogRepoSpringDataInterface actuatorActivityLogRepoSpringDataInterface)
    {
        this.actuatorActivityLogRepoSpringDataInterface = actuatorActivityLogRepoSpringDataInterface;
    }

    @Override
    public ActuatorActivityLog save(ActuatorActivityLog entity)
    {
        ActuatorActivityLogDataModel actuatorActivityLogDataModel = new ActuatorActivityLogDataModel(entity);
        ActuatorActivityLogDataModel result = actuatorActivityLogRepoSpringDataInterface
                .save(actuatorActivityLogDataModel);

        return ActivityLogMapper.toDomain(result);
    }

    @Override
    public Iterable<ActuatorActivityLog> findAll()
    {
        Iterable<ActuatorActivityLogDataModel> actuatorActivityLogDataModels = actuatorActivityLogRepoSpringDataInterface.findAll();
        return ActivityLogMapper.toDomain(actuatorActivityLogDataModels);
    }

    @Override
    public Optional<ActuatorActivityLog> findById(ActivityLogId id)
    {
        Optional<ActuatorActivityLogDataModel> actuatorActivityLogDataModel = actuatorActivityLogRepoSpringDataInterface.findById(id.id);
        return actuatorActivityLogDataModel.map(ActivityLogMapper::toDomain);
    }

    @Override
    public boolean existsById(ActivityLogId id)
    {
        return actuatorActivityLogRepoSpringDataInterface.existsById(id.id);
    }

    @Override
    public Iterable<ActuatorActivityLog> findAllByActuatorIdAndTimestampBetween(long actuatorId, ZonedDateTime startDate, ZonedDateTime endDate)
    {
        Iterable<ActuatorActivityLogDataModel> all = actuatorActivityLogRepoSpringDataInterface
                .findAllByActuatorIdAndTimeStampBetween(actuatorId, startDate, endDate);
        return ActivityLogMapper.toDomain(all);
    }

    @Override
    public ActuatorActivityLog findTopByActuatorIdOrderByTimeStampDesc(long actuatorId)
    {
        ActuatorActivityLogDataModel lastEntry = actuatorActivityLogRepoSpringDataInterface.findTopByActuatorIdOrderByTimeStampDesc(actuatorId);
        if (lastEntry != null)
            return ActivityLogMapper.toDomain(lastEntry);
        return null;
    }

    @Override
    public Iterable<ActuatorActivityLog> findAllByActuatorId(long actuatorId)
    {
        Iterable<ActuatorActivityLogDataModel> actuatorLogs = actuatorActivityLogRepoSpringDataInterface
                .findAllByActuatorId(actuatorId);

        return ActivityLogMapper.toDomain(actuatorLogs);
    }

}
