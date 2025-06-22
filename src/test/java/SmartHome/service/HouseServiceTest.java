package SmartHome.service;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.persistence.mem.HouseRepositoryImplMem;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HouseServiceTest {
    private HouseRepository mockHouseRepository;
    private HouseFactory mockHouseFactory;
    private HouseService houseService;


    @BeforeEach
    void setUp() {
        mockHouseRepository = mock(HouseRepository.class);
        mockHouseFactory = mock(HouseFactory.class);
        houseService = new HouseService(mockHouseRepository, mockHouseFactory);
    }

    @Test
    void validFindHouseById() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        House houseDouble = mock(House.class);
        when(mockHouseFactory.createHouseId(1L)).thenReturn(Optional.of(houseIdDouble));
        when(mockHouseRepository.findById(houseIdDouble)).thenReturn(Optional.of(houseDouble));

        // Act
        House house = houseService.findById(1L);

        // Assert
        assertEquals(houseDouble, house);
    }

    @Test
    void invalidFindHouseById() {
        // Arrange
        long invalidHouseId = 999L;
        HouseId houseIdDouble = mock(HouseId.class);
        when(mockHouseFactory.createHouseId(invalidHouseId)).thenReturn(Optional.of(houseIdDouble));
        when(mockHouseRepository.findById(houseIdDouble)).thenReturn(Optional.empty());

        // Act
        House house = houseService.findById(invalidHouseId);

        // Assert
        assertNull(house, "House should be null when house is not found");
    }

    @Test
    void validFindAll() {
        // Arrange
        House house1 = mock(House.class);
        House house2 = mock(House.class);
        List<House> expectedHouses = List.of(house1, house2);
        when(mockHouseRepository.findAll()).thenReturn(expectedHouses);

        // Act
        Iterable<House> houses = houseService.findAll();

        // Assert
        assertEquals(expectedHouses, houses);
    }

    @Test
    void invalidFindAll() {
        // Arrange
        when(mockHouseRepository.findAll()).thenReturn(List.of());

        // Act
        Iterable<House> houses = houseService.findAll();

        // Assert
        assertTrue(((List<House>) houses).isEmpty());
    }

    @Test
    void validHouseExistsById() {
        // Arrange
        long houseId = 1L;
        HouseId houseIdDouble = mock(HouseId.class);

        when(mockHouseFactory.createHouseId(houseId)).thenReturn(Optional.of(houseIdDouble));
        when(mockHouseRepository.existsById(houseIdDouble)).thenReturn(true);

        // Act
        Pair<Boolean, HouseId> result = houseService.existsById(houseId);

        // Assert
        assertTrue(result.getLeft());
        assertEquals(houseIdDouble, result.getRight());
    }

    @Test
    void invalidHouseExistsById() {
        // Arrange
        HouseId houseIdDouble = mock(HouseId.class);
        when(mockHouseFactory.createHouseId(999L)).thenReturn(Optional.of(houseIdDouble));
        when(mockHouseRepository.existsById(houseIdDouble)).thenReturn(false);

        // Act
        Pair<Boolean, HouseId> exists = houseService.existsById(999L);

        // Assert
        assertFalse(exists.getLeft());
        assertEquals(houseIdDouble, exists.getRight());
    }

    @Test
    void validConfigureLocation() {
        // Arrange
        long id = 1L;
        String street = "Street 1";
        String street2 = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 10.0;
        double longitude = 20.0;
        double longitude2 = 50.0;
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        Address address = new Address(street2, doorNumber, zipCode, city, country);
        GPS gps = new GPS(latitude, longitude2);
        HouseService houseService = new HouseService(houseRepositoryImplMem, houseFactory);

        House house = new HouseFactory().createHouse(new HouseId(id),
                new Address(street, doorNumber, zipCode, city, country), new GPS(latitude, longitude));
        houseRepositoryImplMem.save(house);

        // Act
        House result = houseService.configureLocation(id, address, gps);

        // Assert
        assertNotNull(result);
    }

    @Test
    void validConfigureLocation_errorCreatingAddress() {
        // Arrange
        long id = 1L;
        String street = "Street 1";
        String street2 = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 10.0;
        double longitude = 20.0;
        double longitude2 = 50.0;
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();

        Address address = new Address(street2, doorNumber, zipCode, city, country);
        GPS gps = new GPS(latitude, longitude2);
        HouseService houseService = new HouseService(houseRepositoryImplMem, mockHouseFactory);

        when(mockHouseFactory.createAddress(any(), any(), any(), any(), any())).thenReturn(Optional.empty());

        House house = new HouseFactory().createHouse(new HouseId(id),
                new Address(street, doorNumber, zipCode, city, country), new GPS(latitude, longitude));
        houseRepositoryImplMem.save(house);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> houseService.configureLocation(id, address, gps));
    }

    @Test
    void invalidConfigureLocation_invalidStreet() {
        // Arrange
        long id = 1L;
        String street2 = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 10.0;
        double longitude2 = 50.0;
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        Address address = new Address(street2, doorNumber, zipCode, city, country);
        GPS gps = new GPS(latitude, longitude2);
        HouseService houseService = new HouseService(houseRepositoryImplMem, houseFactory);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> houseService.configureLocation(id, address, gps));
    }

    @Test
    void ShouldAddHouse()
    {
        // Arrange
        HouseId houseId = new HouseId(1L);
        String street = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 10.0;
        double longitude2 = 50.0;
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPS gps = new GPS(latitude, longitude2);
        HouseService houseService = new HouseService(houseRepositoryImplMem, houseFactory);

        // Act
        House result = houseService.add(houseId, address, gps);

        // Assert
        assertEquals(houseId.id, result.identity().id);
    }

    @Test
    void shouldNotAddHouseInvalidHouseIdAttributes()
    {
        // Arrange
        HouseId houseId = null;
        String street = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 10.0;
        double longitude2 = 50.0;
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPS gps = new GPS(latitude, longitude2);
        HouseService houseService = new HouseService(houseRepositoryImplMem, houseFactory);
        String expectedMessage = "Invalid house attributes";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> houseService.add(houseId, address, gps));

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldNotAddHouseInvalidAddressAttributes()
    {
        // Arrange
        double latitude = 10.0;
        double longitude2 = 50.0;
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        GPS gps = new GPS(latitude, longitude2);
        HouseService houseService = new HouseService(houseRepositoryImplMem, houseFactory);
        String expectedMessage = "Invalid house attributes";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
              () -> houseService.add(null, null, gps));

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void shouldNotAddHouseInvalidGpsAttributes()
    {
        // Arrange
        HouseId houseId = new HouseId(1L);
        String street = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        HouseRepository houseRepositoryImplMem = new HouseRepositoryImplMem();
        HouseFactory houseFactory = new HouseFactory();
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPS gps = null;
        HouseService houseService = new HouseService(houseRepositoryImplMem, houseFactory);
        String expectedMessage = "Invalid house attributes";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> houseService.add(houseId, address, gps));

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
