package SmartHome.domain.house;

import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseFactory {

    public House createHouse(HouseId houseID, Address address, GPS gps) {
        try {
            return new House(houseID, address, gps);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Optional<Address> createAddress(String street, String doorNumber, String zipCode, String city, String country) {
        try {
            return Optional.of(new Address(street, doorNumber, zipCode, city, country));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<GPS> createGPS(double latitude, double longitude) {
        try {
            return Optional.of(new GPS(latitude, longitude));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<HouseId> createHouseId(long id) {
        try {
            return Optional.of(new HouseId(id));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
