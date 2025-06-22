package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;

public class Floor extends ValueObject {

    public String floor;

    public Floor(String floor) {
        if (!validateArguments(floor)) throw new IllegalArgumentException("Invalid arguments passed to the constructor");
        this.floor = floor;
    }

    private boolean validateArguments(String floor) {
        return floor != null && !floor.isEmpty();
    }


}
