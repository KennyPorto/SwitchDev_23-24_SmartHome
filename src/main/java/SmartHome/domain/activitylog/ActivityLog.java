package SmartHome.domain.activitylog;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;

public interface ActivityLog extends AggregateRoot<ActivityLogId>
{
    TimeStamp getLogTime();

    Measurement getMeasurement();
}
