package SmartHome.mapper;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.persistence.jpa.datamodel.HouseDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HouseMapperTest {

    private HouseId houseId;
    private Address address;
    private GPS gps;
    private House house;

    @BeforeEach
    void setUp() {
        HouseFactory houseFactory = mock(HouseFactory.class);
        houseId = mock(HouseId.class);
        address = mock(Address.class);
        gps = mock(GPS.class);
        house = mock(House.class);

        when(houseFactory.createHouse(houseId, address, gps)).thenReturn(house);
        when(house.identity()).thenReturn(houseId);
        when(house.getAddress()).thenReturn(address);
        when(house.getGPS()).thenReturn(gps);
    }

    @Test
    void getHouseDTO() {
        // Arrange & Act
        HouseDTO houseDTO = HouseMapper.houseToDTO(house);

        // Assert
        assertEquals(houseId.id, houseDTO.houseId);
        assertEquals(address.street, houseDTO.street);
        assertEquals(address.doorNumber, houseDTO.doorNumber);
        assertEquals(address.zipCode, houseDTO.zipCode);
        assertEquals(address.city, houseDTO.city);
        assertEquals(address.country, houseDTO.country);
        assertEquals(gps.latitude, houseDTO.latitude);
        assertEquals(gps.longitude, houseDTO.longitude);
    }

    @Test
    void getListHousesDTO() {
        // Arrange
        List<House> houses = new ArrayList<>();
        House house2 = mock(House.class);
        HouseId houseId2 = mock(HouseId.class);
        Address address2 = mock(Address.class);
        GPS gps2 = mock(GPS.class);
        when(house2.identity()).thenReturn(houseId2);
        when(house2.getAddress()).thenReturn(address2);
        when(house2.getGPS()).thenReturn(gps2);
        houses.add(house);
        houses.add(house2);

        // Act
        Iterable<HouseDTO> houseDTOList = HouseMapper.houseListToDTO(houses);
        Iterator<HouseDTO> iterator = houseDTOList.iterator();
        HouseDTO houseDTO = iterator.next();

        // Assert
        assertEquals(houseId.id, houseDTO.houseId);
        assertEquals(address.street, houseDTO.street);
        assertEquals(address.doorNumber, houseDTO.doorNumber);
        assertEquals(address.zipCode, houseDTO.zipCode);
        assertEquals(address.city, houseDTO.city);
        assertEquals(address.country, houseDTO.country);
        assertEquals(gps.latitude, houseDTO.latitude);
        assertEquals(gps.longitude, houseDTO.longitude);
    }

    @Test
    void getDomainHouseFromHouseModel() {
        // Arrange
        HouseDataModel houseDataModel = mock(HouseDataModel.class);
        when(houseDataModel.getHouseId()).thenReturn(1L);
        when(houseDataModel.getStreet()).thenReturn("s1");
        when(houseDataModel.getDoorNumber()).thenReturn("d1");
        when(houseDataModel.getZipCode()).thenReturn("4000-000");
        when(houseDataModel.getCity()).thenReturn("c1");
        when(houseDataModel.getCountry()).thenReturn("Portugal");
        when(houseDataModel.getLatitude()).thenReturn(1.0);
        when(houseDataModel.getLongitude()).thenReturn(1.0);

        HouseId houseId = new HouseId(houseDataModel.getHouseId());
        Address address = new Address(houseDataModel.getStreet(), houseDataModel.getDoorNumber(), houseDataModel.getZipCode(),
                houseDataModel.getCity(), houseDataModel.getCountry());
        GPS gps = new GPS(houseDataModel.getLatitude(), houseDataModel.getLongitude());

        House house = new HouseFactory().createHouse(houseId, address, gps);

        // Assert
        assertTrue(HouseMapper.houseDataModelToDomain(houseDataModel).sameAs(house));
    }

    @Test
    void getDomainHouseListFromHouseModelList() {
        // Arrange
        int firstElement = 0;
        HouseDataModel houseDataModel = mock(HouseDataModel.class);
        when(houseDataModel.getHouseId()).thenReturn(1L);
        when(houseDataModel.getStreet()).thenReturn("s1");
        when(houseDataModel.getDoorNumber()).thenReturn("d1");
        when(houseDataModel.getZipCode()).thenReturn("4000-000");
        when(houseDataModel.getCity()).thenReturn("c1");
        when(houseDataModel.getCountry()).thenReturn("Portugal");
        when(houseDataModel.getLatitude()).thenReturn(1.0);
        when(houseDataModel.getLongitude()).thenReturn(1.0);
        List<HouseDataModel> houseDataModels = new ArrayList<>();
        houseDataModels.add(houseDataModel);

        HouseId houseId = new HouseId(houseDataModel.getHouseId());
        Address address = new Address(houseDataModel.getStreet(), houseDataModel.getDoorNumber(), houseDataModel.getZipCode(),
                houseDataModel.getCity(), houseDataModel.getCountry());
        GPS gps = new GPS(houseDataModel.getLatitude(), houseDataModel.getLongitude());

        House house = new HouseFactory().createHouse(houseId, address, gps);

        List<House> houses = new ArrayList<>();
        houses.add(house);

        // Assert
        assertTrue(HouseMapper.housesDataModelToDomain(houseDataModels).iterator().next().sameAs(houses.get(firstElement)));
    }

}
