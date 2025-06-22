package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;

import java.util.Objects;

public class SensorId implements DomainId {
    public long id;

    public SensorId(long id) {
        if (! validateArgument(id))
            throw new IllegalArgumentException("Sensor ID cannot be smaller than 0");
        this.id = id;
    }
    private boolean validateArgument(long id) {
        return id > 0;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        SensorId that = (SensorId) object;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
