package SmartHome.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
public class PeakPowerDTO extends RepresentationModel<PeakPowerDTO> {
    private final double peakPower;
}
