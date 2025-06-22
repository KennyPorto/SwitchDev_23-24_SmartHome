package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class DeviceModel extends ValueObject {

    public final String model;

    public DeviceModel(String model) {
        if (!validateArgument(model))
            throw new IllegalArgumentException("Device model cannot be null or empty");
        this.model = model;
    }

    private boolean validateArgument(String model) {
        return model != null && !model.isEmpty() && !model.isBlank();
    }
}
