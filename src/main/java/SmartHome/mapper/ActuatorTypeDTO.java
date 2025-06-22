package SmartHome.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class ActuatorTypeDTO extends RepresentationModel<ActuatorTypeDTO> {
    @JsonProperty("actuatorTypeId")
    public final String name;
    public final String measurementUnit;
}
