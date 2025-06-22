package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.valueObjects.MeasurementUnit;
import jakarta.persistence.*;

@Entity
@Table(name = "sensor_type")

public class SensorTypeDataModel
{

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;

    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    public SensorTypeDataModel()
    {
    }

    public SensorTypeDataModel(SensorType sensorType)
    {
        this.id = sensorType.identity().id;
        this.measurementUnit = sensorType.getMeasurementUnit();
    }

    public String getId()
    {
        return id;
    }

    public MeasurementUnit getMeasurementUnit()
    {
        return measurementUnit;
    }

}
