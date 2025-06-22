package SmartHome.domain.house;

import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HouseFactoryTest {

    @Test
    void validCreateHouse() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        HouseId houseID = new HouseId(1);
        Address address = mock(Address.class);
        GPS gps = mock(GPS.class);

        // Act
        House house = houseFactory.createHouse(houseID, address, gps);

        // Assert
        assertEquals(houseID, house.identity());
        assertEquals(address, house.getAddress());
        assertEquals(gps, house.getGPS());
    }

    @Test
    void invalidCreateHouse() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        HouseId houseID = new HouseId(1);
        Address invalidAddress = mock(Address.class);

        // Act
        House house = houseFactory.createHouse(houseID, invalidAddress, null);

        // Assert
        assertNull(house);
    }

    @Test
    void createAddress() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";

        // Act
        Optional<Address> optionalAddress = houseFactory.createAddress(street, doorNumber, zipCode, city, country);

        // Assert
        assertTrue(optionalAddress.isPresent());
    }

    @Test
    void createAddressInvalid() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Invalid";

        // Act
        Optional<Address> optionalAddress = houseFactory.createAddress(street, doorNumber, zipCode, city, country);

        // Assert
        assertTrue(optionalAddress.isEmpty());
    }

    @Test
    void createGPS() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        double latitude = 41.1496;
        double longitude = -8.611;

        // Act
        Optional<GPS> optionalGPS = houseFactory.createGPS(latitude, longitude);

        // Assert
        assertTrue(optionalGPS.isPresent());
    }

    @Test
    void createGPSInvalid() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        double latitude = 91;
        double longitude = -181;

        // Act
        Optional<GPS> optionalGPS = houseFactory.createGPS(latitude, longitude);

        // Assert
        assertTrue(optionalGPS.isEmpty());
    }

    @Test
    void createHouseId() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        long id = 1;

        // Act
        Optional<HouseId> optionalHouseId = houseFactory.createHouseId(id);

        // Assert
        assertTrue(optionalHouseId.isPresent());
    }

    @Test
    void createHouseIdInvalid() {
        // Arrange
        HouseFactory houseFactory = new HouseFactory();
        long id = -1;

        // Act
        Optional<HouseId> optionalHouseId = houseFactory.createHouseId(id);

        // Assert
        assertTrue(optionalHouseId.isEmpty());
    }

}
