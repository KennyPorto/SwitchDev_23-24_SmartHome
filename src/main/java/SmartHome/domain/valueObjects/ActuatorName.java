package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class ActuatorName extends ValueObject {
    public final String name;

    public ActuatorName(String name) {
        if (!validateArgument(name))
            throw new IllegalArgumentException("Actuator name cannot be null or empty");
        this.name = name;
    }

    private boolean validateArgument(String name) {
        return name != null && !name.isEmpty() && !name.isBlank();
    }
}
