package SmartHome.mapper;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
public class ActuatorDTO extends RepresentationModel<ActuatorDTO>
{
    public String name;
    public String model;
    public long actuatorId;
    public long deviceId;
    public String actuatorType;
    public int lowerLimit;
    public int upperLimit;
    public double lowerLimitFractional;
    public double upperLimitFractional;
    public int currentValue;

    public ActuatorDTO(String name, String model, long deviceId, String actuatorType)
    {
        this.name = name;
        this.model = model;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
    }

    public ActuatorDTO(String name, String model, long actuatorId, long deviceId, String actuatorType) {
        this.name = name;
        this.model = model;
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
    }

    public ActuatorDTO(String name, String model, long actuatorId, long deviceId, String actuatorType,
                       int lowerLimit, int upperLimit) {
        this.name = name;
        this.model = model;
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public ActuatorDTO(String name, String model, long deviceId, String actuatorType,
                       int lowerLimit, int upperLimit) {
        this.name = name;
        this.model = model;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public ActuatorDTO(String name, String model, long actuatorId, long deviceId, String actuatorType,
                       double lowerLimitFractional, double upperLimitFractional) {
        this.name = name;
        this.model = model;
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
        this.lowerLimitFractional = lowerLimitFractional;
        this.upperLimitFractional = upperLimitFractional;
    }

    public ActuatorDTO(String name, String model, long deviceId, String actuatorType,
                       double lowerLimitFractional, double upperLimitFractional) {
        this.name = name;
        this.model = model;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
        this.lowerLimitFractional = lowerLimitFractional;
        this.upperLimitFractional = upperLimitFractional;
    }

    public ActuatorDTO(String name, String model, long actuatorId, long deviceId, String actuatorType,
                       int currentValue)
    {
        this.name = name;
        this.model = model;
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
        this.currentValue = currentValue;
    }

    public ActuatorDTO(String name, String model, long deviceId, String actuatorType,
                       int currentValue)
    {
        this.name = name;
        this.model = model;
        this.deviceId = deviceId;
        this.actuatorType = actuatorType;
        this.currentValue = currentValue;
    }
}
