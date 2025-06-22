package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.valueObjects.MeasurementUnit;
import jakarta.persistence.*;

@Entity
@Table(name = "actuator_type")

public class ActuatorTypeDataModel
{

    @Id
    private String id;

    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;

    public ActuatorTypeDataModel()
    {
    }

    public ActuatorTypeDataModel(ActuatorType actuatorType)
    {
        this.id = actuatorType.identity().name;
        this.measurementUnit = actuatorType.getMeasurementUnit();
    }

    public String getId()
    {
        return id;
    }

    public MeasurementUnit getMeasurementUnit()
    {
        return measurementUnit;
    }

    public int getVersion()
    {
        return version;
    }
}
