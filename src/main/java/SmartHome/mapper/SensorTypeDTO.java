package SmartHome.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class SensorTypeDTO extends RepresentationModel<SensorTypeDTO> {
    @JsonProperty("sensorTypeId")
    public final String id;
    public final String measurementUnit;
}
