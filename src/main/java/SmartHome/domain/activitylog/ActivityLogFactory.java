package SmartHome.domain.activitylog;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.*;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ActivityLogFactory
{
    public Optional<ActuatorActivityLog> createActuatorActivityLog(ActivityLogId activityLogId, ActuatorId actuatorId, TimeStamp timeStamp, Measurement value)
    {
        try {
            return Optional.of(new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, value));
        } catch ( Exception e ) {
            return Optional.empty();
        }
    }

    public Optional<SensorActivityLog> createSensorActivityLog(ActivityLogId activityLogId, SensorId sensorId, TimeStamp timeStamp, Measurement value)
    {
        try {
            return Optional.of(new SensorActivityLog(activityLogId, timeStamp, sensorId, value));
        } catch ( Exception e ) {
            return Optional.empty();
        }
    }

    public Optional<TimeStamp> createTimeStamp(ZonedDateTime timeStamp)
    {
        try {
            return Optional.of(new TimeStamp(timeStamp));
        } catch ( Exception e ) {
            return Optional.empty();
        }
    }

    public Optional<Measurement> createMeasurement(String measurement)
    {
        try {
            return Optional.of(new Measurement(measurement));
        } catch ( Exception e ) {
            return Optional.empty();
        }
    }

    public Optional<ActivityLogId> createActivityLogId(long id)
    {
        try {
            return Optional.of(new ActivityLogId(id));
        } catch ( Exception e ) {
            return Optional.empty();
        }
    }
}
