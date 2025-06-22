package SmartHome.mapper;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class MaxInstantTempDiffDTO extends RepresentationModel<MaxInstantTempDiffDTO>
{
    public final double tempDiff;
}
