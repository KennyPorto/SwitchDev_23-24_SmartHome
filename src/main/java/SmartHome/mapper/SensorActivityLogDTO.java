package SmartHome.mapper;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
public class SensorActivityLogDTO extends RepresentationModel<SensorActivityLogDTO>
{
    public long sensorActivityLogId = 1;
    public long sensorId;
    public String timeStamp;
    public String measurement;

    public SensorActivityLogDTO(long sensorId, String timeStamp, String measurement)
    {
        this.sensorId = sensorId;
        this.timeStamp = timeStamp;
        this.measurement = measurement;
    }

    public SensorActivityLogDTO(long sensorActivityLogId, long sensorId, String timeStamp, String measurement)
    {
        this.sensorActivityLogId = sensorActivityLogId;
        this.sensorId = sensorId;
        this.timeStamp = timeStamp;
        this.measurement = measurement;
    }
}
