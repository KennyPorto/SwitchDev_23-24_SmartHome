package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class DeviceName extends ValueObject {

    public String name;

    public DeviceName(String name) {
        if (validateArguments(name))
            this.name = name;
        else
            throw new IllegalArgumentException("Parameter value 'name' must be a non-empty string.");
    }

    private boolean validateArguments(String name) {
        return name != null && !name.isBlank() && !name.isEmpty();
    }
}
