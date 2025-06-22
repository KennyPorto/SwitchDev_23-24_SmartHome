package SmartHome.mapper;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
public class SensorDTO extends RepresentationModel<SensorDTO>
{
    public String name;
    public String model;
    public long sensorId = 1;
    public long deviceId;
    public String sensorType;

    public SensorDTO(String name, String model, long sensorId, long deviceId, String sensorType) {
        this.name = name;
        this.model = model;
        this.sensorId = sensorId;
        this.deviceId = deviceId;
        this.sensorType = sensorType;
    }

}
