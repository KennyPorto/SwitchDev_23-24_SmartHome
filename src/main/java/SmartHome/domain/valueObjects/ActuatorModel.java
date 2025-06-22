package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;
import lombok.Getter;

@Getter
public class ActuatorModel extends ValueObject {
    public final String model;

    public ActuatorModel(String model) {
        if (!validateArgument(model))
            throw new IllegalArgumentException("Actuator model cannot be null or empty");
        this.model = model;
    }

    private boolean validateArgument(String model) {
        return model != null && !model.isEmpty() && !model.isBlank();
    }
}
