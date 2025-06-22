package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class RoomName extends ValueObject {
    public final String name;

    public RoomName(String name) {
        if(!validateArguments(name)) {
            throw new IllegalArgumentException("Room name cannot be empty");
        }
        this.name = name;
    }
    private boolean validateArguments(String name) {
        return name != null && !name.isBlank() && !name.isEmpty();
    }
}
