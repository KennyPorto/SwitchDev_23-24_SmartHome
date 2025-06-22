package SmartHome.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
public class ActuatorActivityLogDTO extends RepresentationModel<ActuatorActivityLogDTO>
{
    public long actuatorActivityLogId = 1;
    public long actuatorId;
    public String timeStamp;
    public String measurement;
}
