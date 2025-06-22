package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;

import java.time.ZonedDateTime;

public interface ActuatorActivityLogRepository extends Repository<ActivityLogId, ActuatorActivityLog>
{
    Iterable<ActuatorActivityLog> findAllByActuatorIdAndTimestampBetween(long actuatorId, ZonedDateTime startDate, ZonedDateTime endDate);

    ActuatorActivityLog findTopByActuatorIdOrderByTimeStampDesc(long actuatorId);

    Iterable<ActuatorActivityLog> findAllByActuatorId(long actuatorId);
}
