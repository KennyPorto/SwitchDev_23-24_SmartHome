package SmartHome.mapper;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.persistence.jpa.datamodel.HouseDataModel;

import java.util.ArrayList;
import java.util.List;

public class HouseMapper
{

    public static HouseDTO houseToDTO(House house)
    {
        return new HouseDTO(house.identity().id, house.getAddress().street, house.getAddress().doorNumber,
              house.getAddress().zipCode, house.getAddress().city, house.getAddress().country,
              house.getGPS().latitude, house.getGPS().longitude);
    }

    public static Iterable<HouseDTO> houseListToDTO(Iterable<House> houses)
    {
        List<HouseDTO> houseListDTO = new ArrayList<>();

        houses.forEach(house ->
        {
            HouseDTO houseDTO = HouseMapper.houseToDTO(house);
            houseListDTO.add(houseDTO);
        });

        return houseListDTO;
    }

    public static Iterable<House> housesDataModelToDomain(Iterable<HouseDataModel> houseDataModels)
    {
        List<House> houses = new ArrayList<>();

        for ( HouseDataModel houseDm : houseDataModels ) {
            HouseId houseId = new HouseId(houseDm.getHouseId());
            Address address = new Address(houseDm.getStreet(), houseDm.getDoorNumber(), houseDm.getZipCode(),
                  houseDm.getCity(), houseDm.getCountry());
            GPS gps = new GPS(houseDm.getLatitude(), houseDm.getLongitude());

            House house = new HouseFactory().createHouse(houseId, address, gps);

            houses.add(house);
        }

        return houses;
    }

    public static House houseDataModelToDomain(HouseDataModel houseDataModel)
    {
        HouseId houseId = new HouseId(houseDataModel.getHouseId());
        Address address = new Address(houseDataModel.getStreet(), houseDataModel.getDoorNumber(),
              houseDataModel.getZipCode(), houseDataModel.getCity(), houseDataModel.getCountry());
        GPS gps = new GPS(houseDataModel.getLatitude(), houseDataModel.getLongitude());

        return new HouseFactory().createHouse(houseId, address, gps);
    }
}


