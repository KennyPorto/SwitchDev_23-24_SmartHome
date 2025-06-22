package SmartHome.controller.cli_controllers;

import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.mapper.AddressDTO;
import SmartHome.mapper.GpsDTO;
import SmartHome.service.HouseService;

public class Ctrl01ConfigureHouseLocation
{
    private final HouseService houseService;

    public Ctrl01ConfigureHouseLocation(HouseService houseService)
    {
        this.houseService = houseService;
    }

    public boolean configureLocation(long houseId, AddressDTO addressDTO, GpsDTO gpsDTO)
    {

        if (addressDTO == null || gpsDTO == null) return false;

        try {
            Address address = new Address(addressDTO.street, addressDTO.doorNumber, addressDTO.zipCode, addressDTO.city, addressDTO.country);
            GPS gps = new GPS(gpsDTO.latitude, gpsDTO.longitude);
            this.houseService.configureLocation(houseId, address, gps);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
