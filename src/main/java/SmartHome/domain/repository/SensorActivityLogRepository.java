package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;

import java.time.ZonedDateTime;

public interface SensorActivityLogRepository extends Repository<ActivityLogId, SensorActivityLog>
{
    Iterable<SensorActivityLog> findAllBySensorIdAndTimestampBetween(long sensorId, ZonedDateTime startDate, ZonedDateTime endDate);

    Iterable<SensorActivityLog> findAllBySensorId(long sensorId);

}

