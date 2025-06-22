package SmartHome.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
public class HouseDTO extends RepresentationModel<HouseDTO>
{
    public long houseId = 1;
    public String street;
    public String doorNumber;
    public String zipCode;
    public String city;
    public String country;
    public double latitude;
    public double longitude;

    public HouseDTO(String street, String doorNumber, String zipCode, String city, String country,
                    double latitude, double longitude)
    {
        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}


