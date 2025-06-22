package SmartHome.domain.repository;

import SmartHome.ddd.Repository;
import SmartHome.domain.house.House;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;

public interface HouseRepository extends Repository<HouseId, House> {
    House configureLocation(long houseId, Address address, GPS gps);
}
