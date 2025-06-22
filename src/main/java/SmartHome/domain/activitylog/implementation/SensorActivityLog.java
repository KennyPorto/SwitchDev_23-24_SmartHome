package SmartHome.domain.activitylog.implementation;

import SmartHome.domain.activitylog.ActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.SensorId;
import SmartHome.domain.valueObjects.TimeStamp;

import java.util.Objects;

public class SensorActivityLog implements ActivityLog
{
    private final ActivityLogId _activityLogId;
    private final TimeStamp _timeStamp;
    private final SensorId _sensorId;
    private final Measurement _measurement;

    public SensorActivityLog(ActivityLogId activityLogId, TimeStamp timeStamp, SensorId sensorId, Measurement measurement)
    {
        validateArguments(activityLogId, timeStamp, sensorId, measurement);

        _activityLogId = activityLogId;
        _timeStamp = timeStamp;
        _sensorId = sensorId;
        _measurement = measurement;
    }

    @Override
    public TimeStamp getLogTime()
    {
        return this._timeStamp;
    }

    public SensorId getSensorId()
    {
        return this._sensorId;
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

        SensorActivityLog that = (SensorActivityLog) object;

        return Objects.equals(_activityLogId, that._activityLogId) &&
                Objects.equals(_timeStamp, that._timeStamp) &&
                Objects.equals(_sensorId, that._sensorId) &&
              Objects.equals(_measurement, that._measurement);
    }

    private void validateArguments(ActivityLogId activityLogId, TimeStamp timeStamp, SensorId sensorId, Measurement measurement)
    {
        if ( activityLogId == null ) {
            throw new IllegalArgumentException("ActivityLogId cannot be null");
        }

        if ( timeStamp == null ) {
            throw new IllegalArgumentException("TimeStamp cannot be null");
        }

        if ( sensorId == null ) {
            throw new IllegalArgumentException("SensorId cannot be null");
        }

        if ( measurement == null ) {
            throw new IllegalArgumentException("Value cannot be null");
        }
    }
}
