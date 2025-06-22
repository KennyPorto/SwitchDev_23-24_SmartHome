package SmartHome.domain.house;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;

public class House implements AggregateRoot<HouseId> {
    private final HouseId _houseId;
    private Address _address;
    private GPS _gps;

    protected House(HouseId houseId, Address address, GPS gps) {
        if (houseId == null || address == null || gps == null)
            throw new IllegalArgumentException("Invalid arguments passed to the constructor");
        this._houseId = houseId;
        this._address = address;
        this._gps = gps;
    }

    public Address getAddress() {
        return this._address;
    }

    public GPS getGPS() {
        return this._gps;
    }

    public House configureLocation(Address address, GPS gps) {
        this._address = address;
        this._gps = gps;
        return this;
    }

    @Override
    public HouseId identity() {
        return this._houseId;
    }

    @Override
    public boolean sameAs(Object object) {
        if (object instanceof House) {
            House house = (House) object;

            return this._houseId.equals(house._houseId) && this._address.equals(house._address) && this._gps.equals(house._gps);
        }
        return false;
    }
}
