package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;

import java.util.Objects;

public class HouseId implements DomainId {
    public long id;

    public HouseId(long id) {
        if (!validateArguments(id)) throw new IllegalArgumentException("Invalid arguments passed to the constructor");
        this.id = id;
    }

    private boolean validateArguments(long id) {
        return id > 0;
    }

    public boolean equals(Object object) {
        if (object instanceof HouseId houseId) {

            return this.id == houseId.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
