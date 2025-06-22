package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;

import java.util.Objects;

public class RoomId implements DomainId {
    public long id;

    public RoomId(long id) {
        if (!validateArguments(id)) throw new IllegalArgumentException("Invalid arguments passed to the constructor");
        this.id = id;
    }

    private boolean validateArguments(long id) {
        return id >= 0;
    }

    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof RoomId) {
            RoomId newRoomId = (RoomId) object;

            return this.id == newRoomId.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
