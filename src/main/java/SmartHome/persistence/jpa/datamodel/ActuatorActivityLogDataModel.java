package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "actuatoractivitylog")
public class ActuatorActivityLogDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityLogId;
    @Column(name = "actuator_id")
    private long actuatorId;
    @Column(name = "timestamp")
    private ZonedDateTime timeStamp;
    @Column(name = "measurement")
    private String measurement;

    public ActuatorActivityLogDataModel()
    {
    }

    public ActuatorActivityLogDataModel(ActuatorActivityLog actuatorActivityLog)
    {
        this.actuatorId = actuatorActivityLog.getActuatorId().id;
        this.timeStamp = actuatorActivityLog.getLogTime().value;
        this.measurement = actuatorActivityLog.getMeasurement().measurement;
    }

    public long getActivityLogId()
    {
        return activityLogId;
    }

    public long getActuatorId()
    {
        return actuatorId;
    }

    public ZonedDateTime getTimeStamp()
    {
        return timeStamp;
    }

    public String getMeasurement()
    {
        return measurement;
    }
}
