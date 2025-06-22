package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "sensoractivitylog")
public class SensorActivityLogDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sensoractivitylog_id")
    private long sensorActivityLogId;
    @Column(name = "sensor_id")
    private long sensorId;
    @Column(name = "timestamp")
    private ZonedDateTime timeStamp;
    @Column(name = "measurement")
    private String measurement;

    public SensorActivityLogDataModel()
    {
    }

    public SensorActivityLogDataModel(SensorActivityLog sensorActivityLog)
    {
        this.sensorId = sensorActivityLog.getSensorId().id;
        this.timeStamp = sensorActivityLog.getLogTime().value;
        this.measurement = sensorActivityLog.getMeasurement().measurement;
    }

    public long getSensorActivityLogId()
    {
        return sensorActivityLogId;
    }

    public long getSensorId()
    {
        return sensorId;
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
