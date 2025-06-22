package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;
import lombok.Getter;

import java.util.Objects;

@Getter
public class DeviceID implements DomainId {
    public long id;

    public DeviceID(long id){
        if (! validateIdArgument(id))
            throw new IllegalArgumentException("Device ID cannot be smaller than 0");
        this.id = id;
    }
    private boolean validateIdArgument(long id) {
        return id >= 0;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DeviceID that = (DeviceID) object;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
