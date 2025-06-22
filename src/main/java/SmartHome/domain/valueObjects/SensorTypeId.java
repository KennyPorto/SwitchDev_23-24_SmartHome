package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;
import SmartHome.ddd.ValueObject;

public class SensorTypeId extends ValueObject implements DomainId {
    public final String id;

    public SensorTypeId(String id) {
        if (!validateArguments(id)) throw new IllegalArgumentException();
        this.id = id;
    }
    private boolean validateArguments(String name) {
        if (name == null) return false;
        return !name.trim().isEmpty();
    }
    @Override
    public String toString() {
        return id;
    }
}
