package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;
import lombok.Getter;

import java.util.Objects;
@Getter
public class ActuatorId implements DomainId {
    public final long id;

    public ActuatorId(long id) {
        if (! validateArgument(id))
            throw new IllegalArgumentException("Actuator ID cannot be smaller than 0");
        this.id = id;
    }

    private boolean validateArgument(long id) {
        return id >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActuatorId that = (ActuatorId) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
