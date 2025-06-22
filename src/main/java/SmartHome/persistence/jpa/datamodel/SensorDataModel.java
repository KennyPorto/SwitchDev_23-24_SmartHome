package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.sensors.Sensor;
import jakarta.persistence.*;

@Entity
@Table(name = "sensor")
public class SensorDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sensorId;

    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;
    private String sensorName;
    private String sensorTypeId;
    private long deviceId;
    private String sensorModel;

    public SensorDataModel()
    {
    }

    public SensorDataModel(Sensor sensor)
    {
        this.sensorName = sensor.getName().name;
        this.sensorTypeId = sensor.getSensorTypeId().id;
        this.deviceId = sensor.getDeviceID().id;
        this.sensorModel = sensor.getSensorModel().model;
    }

    public long getId()
    {
        return sensorId;
    }

    public String getSensorName()
    {
        return sensorName;
    }

    public String getSensorTypeId()
    {
        return sensorTypeId;
    }

    public long getDeviceId()
    {
        return deviceId;
    }

    public String getSensorModel() {return sensorModel; }

    public int getVersion()
    {
        return version;
    }
}