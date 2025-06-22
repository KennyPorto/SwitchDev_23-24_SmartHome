package SmartHome.controller.cli_controllers;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.mapper.AddressDTO;
import SmartHome.mapper.GpsDTO;
import SmartHome.persistence.mem.HouseRepositoryImplMem;
import SmartHome.service.HouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Ctrl01ConfigureHouseLocationTest {
    private Ctrl01ConfigureHouseLocation ctrl01ConfigureHouseLocation;
    private HouseRepository houseRepository;

    @BeforeEach
    public void setUp() {
        houseRepository = new HouseRepositoryImplMem();
        HouseService houseService = new HouseService(houseRepository, new HouseFactory());
        ctrl01ConfigureHouseLocation = new Ctrl01ConfigureHouseLocation(houseService);
    }

    @Test
    void validConfigureLocation() {
        // Arrange
        long houseId = 1L;
        String street = "Street 1";
        String street2 = "New Street";
        String doorNumber = "1";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        double latitude = 10.0;
        double longitude = 20.0;
        double longitude2 = 50.0;
        HouseId houseIdObj = new HouseId(houseId);
        Address address = new Address(street, doorNumber, zipCode, city, country);
        GPS gps = new GPS(latitude, longitude);
        House house = new HouseFactory().createHouse(houseIdObj, address, gps);
        houseRepository.save(house);

        AddressDTO addressDTO = new AddressDTO(street2, doorNumber, zipCode, city, country);
        GpsDTO gpsDTO = new GpsDTO(latitude, longitude2);

        // Act
        boolean result = ctrl01ConfigureHouseLocation.configureLocation(houseId, addressDTO, gpsDTO);

        // Assert
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", " \n"})
    @NullAndEmptySource
    void invalidAddressConfigureLocation(String invalidInput) {
        // Arrange
        long houseId = 1L;
        AddressDTO addressDTO = new AddressDTO(invalidInput, invalidInput, invalidInput, invalidInput, invalidInput);
        GpsDTO gpsDTO = new GpsDTO(10.0, 20.0);

        // Act
        boolean result = ctrl01ConfigureHouseLocation.configureLocation(houseId, addressDTO, gpsDTO);

        // Assert
        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.0, 200.0})
    void invalidGpsConfigureLocation(double invalidInput) {
        long houseId = 1L;
        AddressDTO addressDTO = new AddressDTO("Street 1", "1", "4000-000",
                "City 1", "Portugal");
        GpsDTO gpsDTO = new GpsDTO(invalidInput, invalidInput);

        // Act
        boolean result = ctrl01ConfigureHouseLocation.configureLocation(houseId, addressDTO, gpsDTO);

        // Assert
        assertFalse(result);
    }

    @Test
    void invalidConfigureLocation_addressDtoNull() {
        long houseId = 1L;
        GpsDTO gpsDTO = new GpsDTO(-80, 10);

        // Act
        boolean result = ctrl01ConfigureHouseLocation.configureLocation(houseId, null, gpsDTO);

        // Assert
        assertFalse(result);
    }

    @Test
    void invalidConfigureLocation_gpsDtoNull() {
        long houseId = 1L;
        AddressDTO addressDTO = new AddressDTO("Street 1", "1", "4000-000",
                "City 1", "Portugal");

        // Act
        boolean result = ctrl01ConfigureHouseLocation.configureLocation(houseId, addressDTO, null);

        // Assert
        assertFalse(result);
    }
}