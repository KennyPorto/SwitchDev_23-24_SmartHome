package SmartHome.mapper;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class ErrorResponse extends RepresentationModel<ErrorResponse> {
    public final String error;
}
