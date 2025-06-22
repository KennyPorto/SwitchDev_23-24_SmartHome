package SmartHome.domain.house;

import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HouseTest {

    private HouseId mockedHouseId;
    private Address mockedAddress;
    private GPS mockedGps;

    @BeforeEach
    void setUp() {
        mockedHouseId = mock(HouseId.class);
        mockedAddress = mock(Address.class);
        mockedGps = mock(GPS.class);
    }

    @Test
    void validConstructor() {
        // Arrange & Act
        House house = new House(mockedHouseId, mockedAddress, mockedGps);

        // Assert
        assertNotNull(house);
        assertEquals(mockedAddress, house.getAddress());
        assertEquals(mockedGps, house.getGPS());
    }

    @Test
    void invalidConstructor() {
        // Arrange
        HouseId houseId = mock(HouseId.class);
        Address address = mock(Address.class);
        GPS gps = mock(GPS.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new House(null, address, gps));
        assertThrows(IllegalArgumentException.class, () -> new House(houseId, null, gps));
        assertThrows(IllegalArgumentException.class, () -> new House(houseId, address, null));
    }

    @Test
    void validGetAddress() {
        // Arrange
        House house = new House(mockedHouseId, mockedAddress, mockedGps);

        // Act
        Address result = house.getAddress();

        // Assert
        assertEquals(mockedAddress, result);
    }

    @Test
    void testGetGPS() {
        // Arrange
        House house = new House(mockedHouseId, mockedAddress, mockedGps);

        // Act
        GPS result = house.getGPS();

        // Assert
        assertEquals(mockedGps, result);
    }

    @Test
    void validHouseIdentity() {
        // Arrange
        House house = new House(mockedHouseId, mockedAddress, mockedGps);

        // Act
        HouseId result = house.identity();

        // Assert
        assertEquals(mockedHouseId, result);
    }

    @Test
    void validSameAs() {
        // Arrange
        House house = new House(mockedHouseId, mockedAddress, mockedGps);
        House sameHouse = new House(mockedHouseId, mockedAddress, mockedGps);
        House differentHouse = new House(mock(HouseId.class), mock(Address.class), mock(GPS.class));

        // Act & Assert
        assertTrue(house.sameAs(sameHouse));
        assertFalse(house.sameAs(differentHouse));
    }

    @Test
    void validSameAsWithSameAttributes() {
        // Arrange
        HouseId sharedHouseId = new HouseId(1);
        Address sharedAddress = new Address("123 Main St", "Apt 1", "12345", "New York", "USA");
        GPS sharedGPS = new GPS(40.7128, -74.0060);

        House house1 = new House(sharedHouseId, sharedAddress, sharedGPS);
        House house2 = new House(sharedHouseId, sharedAddress, sharedGPS);

        // Act
        boolean result = house1.sameAs(house2);

        // Assert
        assertTrue(result);
    }

    @Test
    void invalidSameAs() {
        // Arrange
        House house = new House(mockedHouseId, mockedAddress, mockedGps);
        Object object = new Object();

        // Act
        boolean result = house.sameAs(object);

        // Assert
        assertFalse(result);
    }

    @Test
    void configureLocation() {
        // Arrange
        HouseId houseId = new HouseId(1);
        Address initialAddress = new Address("123 Main St", "Apt 1", "12345", "New York", "USA");
        GPS initialGPS = new GPS(40.7128, -74.0060);
        House house = new House(houseId, initialAddress, initialGPS);
        Address newAddress = new Address("456 Elm St", "Apt 2", "54321", "Los Angeles", "USA");
        GPS newGPS = new GPS(34.0522, -118.2437);

        // Act
        House houseUpdated = house.configureLocation(newAddress, newGPS);
        House expected = new House(houseId, newAddress, newGPS);

        // Assert
        assertEquals(expected.identity().id, houseUpdated.identity().id);
        assertEquals(expected.getAddress().street, houseUpdated.getAddress().street);
        assertEquals(expected.getGPS().latitude, houseUpdated.getGPS().latitude);
    }

    @Test
    void houseAggregate_SameAsWithEqualHouses() {
        // Arrange
        HouseId houseId = new HouseId(1);
        Address address = new Address("123 Main St", "Apt 1", "12345", "New York", "USA");
        GPS gps = new GPS(40.7128, -74.0060);
        House house1 = new House(houseId, address, gps);
        House house2 = new House(houseId, address, gps);

        // Act
        boolean result = house1.sameAs(house2);

        // Assert
        assertTrue(result);
    }

    @Test
    void houseAggregate_SameAsWithDifferentHouses() {
        // Arrange
        HouseId houseId1 = new HouseId(1);
        HouseId houseId2 = new HouseId(2);
        Address address1 = new Address("123 Main St", "Apt 1", "12345", "New York", "USA");
        Address address2 = new Address("456 Elm St", "Apt 2", "54321", "Los Angeles", "USA");
        GPS gps1 = new GPS(40.7128, -74.0060);
        GPS gps2 = new GPS(34.0522, -118.2437);
        House house1 = new House(houseId1, address1, gps1);
        House house2 = new House(houseId2, address2, gps2);

        // Act
        boolean result = house1.sameAs(house2);

        // Assert
        assertFalse(result);
    }
}
