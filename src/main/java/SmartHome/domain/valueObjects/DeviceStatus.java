package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class DeviceStatus extends ValueObject {
    public final boolean activated;

    public DeviceStatus(boolean activated) {
        this.activated = activated;
    }
}
