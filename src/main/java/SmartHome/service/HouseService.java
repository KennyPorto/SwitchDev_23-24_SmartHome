package SmartHome.service;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseService
{

    private final HouseRepository _houseRepository;
    private final HouseFactory _houseFactory;

    public HouseService(
            @Qualifier("houseRepoSpringDataImpl") HouseRepository houseRepository,
            HouseFactory houseFactory)
    {
        this._houseRepository = houseRepository;
        this._houseFactory = houseFactory;
    }

    public House configureLocation(long houseId, Address address, GPS gps)
    {
        Optional<Address> newAddress = _houseFactory.createAddress(address.street, address.doorNumber,
                address.zipCode, address.city, address.country);
        Optional<GPS> newGps = _houseFactory.createGPS(gps.latitude, gps.longitude);

        if ( newAddress.isEmpty() ) {
            throw new IllegalArgumentException();
        }
        if ( newGps.isEmpty() ) {
            throw new IllegalArgumentException();
        }

        return _houseRepository.configureLocation(houseId, newAddress.get(), newGps.get());
    }

    public House add(HouseId houseId, Address address, GPS gps)
    {
        House houseToSave = _houseFactory.createHouse(houseId, address, gps);
        if (houseToSave == null) {
            throw new IllegalArgumentException("Invalid house attributes");
        }

        return this._houseRepository.save(houseToSave);
    }

    public Iterable<House> findAll()
    {
        return _houseRepository.findAll();
    }

    public House findById(Long id)
    {
        Optional<HouseId> houseId = _houseFactory.createHouseId(id);
        if (houseId.isPresent()) {
            Optional<House> house = _houseRepository.findById(houseId.get());
            if (house.isPresent()) {
                return house.get();
            }
        }
        return null;
    }

    public Pair<Boolean, HouseId> existsById(Long id) {
        HouseId houseId = _houseFactory.createHouseId(id).orElse(null);
        return Pair.of(_houseRepository.existsById(houseId), houseId);
    }

}