package SmartHome.persistence.springdata;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.repository.SensorActivityLogRepository;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.mapper.ActivityLogMapper;
import SmartHome.persistence.jpa.datamodel.SensorActivityLogDataModel;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public class SensorActivityLogRepoSpringDataImpl implements SensorActivityLogRepository
{
    private final SensorActivityLogRepoSpringDataInterface sensorActivityLogRepoSpringDataInterface;

    public SensorActivityLogRepoSpringDataImpl(SensorActivityLogRepoSpringDataInterface sensorActivityLogRepoSpringDataInterface)
    {
        this.sensorActivityLogRepoSpringDataInterface = sensorActivityLogRepoSpringDataInterface;
    }

    @Override
    public SensorActivityLog save(SensorActivityLog entity)
    {
        SensorActivityLogDataModel sensorDataModel = new SensorActivityLogDataModel(entity);
        SensorActivityLogDataModel result = sensorActivityLogRepoSpringDataInterface.save(sensorDataModel);
        return ActivityLogMapper.sensorDataModelToDomain(result);
    }

    @Override
    public Iterable<SensorActivityLog> findAll()
    {
        Iterable<SensorActivityLogDataModel> sensorActivityLogDataModels = sensorActivityLogRepoSpringDataInterface.findAll();
        return ActivityLogMapper.sensorsDataModelToDomain(sensorActivityLogDataModels);
    }

    @Override
    public Optional<SensorActivityLog> findById(ActivityLogId id)
    {
        Optional<SensorActivityLogDataModel> sensorActivityLogDataModel = sensorActivityLogRepoSpringDataInterface.findById(id.id);
        if (sensorActivityLogDataModel.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ActivityLogMapper.sensorDataModelToDomain(sensorActivityLogDataModel.get()));
    }

    @Override
    public boolean existsById(ActivityLogId id)
    {
        return sensorActivityLogRepoSpringDataInterface.existsById(id.id);
    }

    @Override
    public Iterable<SensorActivityLog> findAllBySensorIdAndTimestampBetween(long sensorId, ZonedDateTime startDate, ZonedDateTime endDate) {
        Iterable<SensorActivityLogDataModel> all = sensorActivityLogRepoSpringDataInterface
              .findAllBySensorIdAndTimeStampBetween(sensorId, startDate, endDate);
        return ActivityLogMapper.sensorsDataModelToDomain(all);
    }

    @Override
    public Iterable<SensorActivityLog> findAllBySensorId(long sensorId) {
        Iterable<SensorActivityLogDataModel> all = sensorActivityLogRepoSpringDataInterface
                .findAllBySensorId(sensorId);
        return ActivityLogMapper.sensorsDataModelToDomain(all);
    }
}
