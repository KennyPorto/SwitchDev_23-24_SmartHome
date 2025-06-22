package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;
import SmartHome.ddd.ValueObject;
import lombok.Getter;

@Getter
public class ActuatorTypeId extends ValueObject implements DomainId {
    public final String name;

    public ActuatorTypeId(String name) {
        if (!validateArguments(name)) throw new IllegalArgumentException();
        this.name = name;
    }

    private boolean validateArguments(String name) {
        return name != null && !name.trim().isEmpty();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
