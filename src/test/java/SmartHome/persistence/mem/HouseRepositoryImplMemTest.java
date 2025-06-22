package SmartHome.persistence.mem;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HouseRepositoryImplMemTest
{
    private HouseRepositoryImplMem houseRepositoryImplMem;
    private House mockHouse;
    private HouseId mockHouseId;

    @BeforeEach
    void setUp() {
        houseRepositoryImplMem = new HouseRepositoryImplMem();
        mockHouse = mock(House.class);
        mockHouseId = mock(HouseId.class);
        when(mockHouse.identity()).thenReturn(mockHouseId);
    }

    @Test
    void validSave() {
        // Arrange & Act
        House result = houseRepositoryImplMem.save(mockHouse);

        // Assert
        assertEquals(mockHouse, result);
    }

    @Test
    void validFindAllHouses() {
        // Arrange
        houseRepositoryImplMem.save(mockHouse);

        // Act
        Iterable<House> result = houseRepositoryImplMem.findAll();

        // Assert
        List<House> house = (StreamSupport.stream(result.spliterator(), false).toList());
        assertTrue(house.contains(mockHouse));
    }

    @Test
    void validExistsById() {
        // Arrange
        houseRepositoryImplMem.save(mockHouse);

        // Act
        boolean result = houseRepositoryImplMem.existsById(mockHouseId);

        // Assert
        assertTrue(result);
    }

    @Test
    void invalidExistsByIdDifferentHouseId() {
        // Arrange
        HouseId houseId = new HouseId(1L);
        HouseId houseId2 = new HouseId(2L);
        House house = mock(House.class);
        when(house.identity()).thenReturn(houseId);
        houseRepositoryImplMem.save(house);

        // Act
        boolean result = houseRepositoryImplMem.existsById(houseId2);

        // Assert
        assertFalse(result);
    }

    @Test
    void doesntExistById() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();

        // Act
        boolean result = houseRepositoryImplMem.existsById(mockHouseId);

        // Assert
        assertFalse(result);
    }

    @Test
    void existsByIdWhenHouseIdIsNull() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();

        // Act
        boolean result = houseRepositoryImplMem.existsById(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void findByIdWhenHouseExists_withoutSave() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseId houseId = new HouseId(1L);

        // Act
        Optional<House> result = houseRepositoryImplMem.findById(houseId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findByIdWhenHouseExists_withSave() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        HouseId houseId = new HouseId(1L);
        Address address = mock(Address.class);
        GPS gps = mock(GPS.class);
        House house = houseFactory.createHouse( houseId, address, gps);

        // Act
        houseRepositoryImplMem.save(house);
        Optional<House> result = houseRepositoryImplMem.findById(houseId);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void findByIdWhenHouseExists_withTwoHouseId() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        HouseId houseId = new HouseId(1L);
        HouseId houseId2 = new HouseId(2L);
        Address address = mock(Address.class);
        GPS gps = mock(GPS.class);
        House house = houseFactory.createHouse( houseId, address, gps);

        // Act
        houseRepositoryImplMem.save(house);
        Optional<House> result = houseRepositoryImplMem.findById(houseId2);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findByIdWhenHouseDoesNotExist() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();

        // Act
        Optional<House> result = houseRepositoryImplMem.findById(mockHouseId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findByIdWhenHouseIdIsNull() {
        // Arrange
        HouseRepositoryImplMem houseRepositoryImplMem = new HouseRepositoryImplMem();

        // Act
        Optional<House> result = houseRepositoryImplMem.findById(null);

        // Assert
        assertTrue(result.isEmpty());
    }

}
