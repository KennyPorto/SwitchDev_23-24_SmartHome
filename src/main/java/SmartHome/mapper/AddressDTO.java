package SmartHome.mapper;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
public class AddressDTO extends RepresentationModel<AddressDTO>
{
    public final String street;
    public final String doorNumber;
    public final String zipCode;
    public final String city;
    public final String country;
}
