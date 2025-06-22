package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.actuators.Actuator;
import jakarta.persistence.*;


@Entity
@Table(name = "actuator")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)

public class ActuatorDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long actuatorId;
    private String actuatorName;
    private String actuatorTypeId;
    private long deviceId;
    private String actuatorModel;

    public ActuatorDataModel()
    {
    }

    public ActuatorDataModel(Actuator actuator)
    {
        this.actuatorId = actuator.identity().id;
        this.actuatorName = actuator.getName().name;
        this.actuatorTypeId = actuator.getActuatorTypeId().name;
        this.deviceId = actuator.getDeviceID().id;
        this.actuatorModel = actuator.getActuatorModel().model;
    }

    public long getActuatorId() {
        return actuatorId;
    }

    public String getActuatorName() {
        return actuatorName;
    }

    public String getActuatorTypeId() {
        return actuatorTypeId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public String getActuatorModel() {
        return actuatorModel;
    }
}
