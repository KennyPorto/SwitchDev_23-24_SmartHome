package SmartHome.domain.activitylog.implementation;

import SmartHome.domain.activitylog.ActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;

import java.util.Objects;

public class ActuatorActivityLog implements ActivityLog
{
    private ActivityLogId _activityLogId;
    private final ActuatorId _actuatorId;
    private final TimeStamp _timeStamp;
    private final Measurement _measurement;

    public ActuatorActivityLog(ActivityLogId activityLogId, ActuatorId actuatorId, TimeStamp timeStamp, Measurement measurement)
    {
        validateArguments(activityLogId, actuatorId, timeStamp, measurement);

        this._activityLogId = activityLogId;
        this._actuatorId = actuatorId;
        this._timeStamp = timeStamp;
        this._measurement = measurement;
    }

    @Override
    public TimeStamp getLogTime()
    {
        return this._timeStamp;
    }

    public ActuatorId getActuatorId()
    {
        return this._actuatorId;
    }

    @Override
    public Measurement getMeasurement()
    {
        return this._measurement;
    }

    @Override
    public ActivityLogId identity()
    {
        return this._activityLogId;
    }

    @Override
    public boolean sameAs(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ActuatorActivityLog that = (ActuatorActivityLog) object;

        return Objects.equals(_activityLogId, that._activityLogId) &&
                Objects.equals(_timeStamp, that._timeStamp) &&
                Objects.equals(_actuatorId, that._actuatorId) &&
                Objects.equals(_measurement, that._measurement);
    }

    private void validateArguments(ActivityLogId activityLogId, ActuatorId actuatorId, TimeStamp timeStamp, Measurement measurement)
    {
        if ( activityLogId == null ) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if ( actuatorId == null ) {
            throw new IllegalArgumentException("ActuatorId cannot be null");
        }

        if (timeStamp == null) {
            throw new IllegalArgumentException("TimeStamp cannot be null");
        }

        if (measurement == null) {
            throw new IllegalArgumentException("Measurement cannot be null");
        }
    }
}
