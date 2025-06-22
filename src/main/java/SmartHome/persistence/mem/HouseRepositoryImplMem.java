package SmartHome.persistence.mem;

import SmartHome.domain.house.House;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HouseRepositoryImplMem implements HouseRepository
{
    private final Map<HouseId, House> _houses = new HashMap<>();

    @Override
    public House save(House house) {
        if (existsById(house.identity())) return null;
        _houses.put(house.identity(), house);
        return house;
    }

    @Override
    public Iterable<House> findAll() {
        return _houses.values();
    }

    @Override
    public boolean existsById(HouseId houseId) {
        Set<HouseId> keys = _houses.keySet();
        for (HouseId key : keys)
            if (key.id == houseId.id)
                return true;
        return false;
    }

    @Override
    public Optional<House> findById(HouseId houseId) {
        Optional<HouseId> idObject = _houses.keySet().stream().filter(h -> houseId.id == h.id).findFirst();
        return idObject.map(_houses::get);
    }

    @Override
    public House configureLocation(long houseId, Address address, GPS gps) {
        HouseId houseId1 = new HouseId(houseId);
        Optional<House> house = findById(houseId1);
        if (house.isEmpty()) throw new IllegalArgumentException();

        return house.get().configureLocation(address, gps);
    }
}
