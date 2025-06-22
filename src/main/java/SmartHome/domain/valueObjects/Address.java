package SmartHome.domain.valueObjects;

import SmartHome.ddd.ValueObject;
import SmartHome.domain.valueObjects.zipCountries.ZipCodeValidator;

import java.lang.reflect.InvocationTargetException;

import static SmartHome.utils.Constants.COUNTRIES_PATH;

public class Address extends ValueObject {

    public final String street;
    public final String doorNumber;
    public final String zipCode;
    public final String city;
    public final String country;
    private ZipCodeValidator _zipCodeValidator;

    public Address(String street, String doorNumber, String zipCode, String city, String country) {

        try {
            String fullPath = COUNTRIES_PATH + country + "ZipCode";
            _zipCodeValidator = (ZipCodeValidator) Class.forName(fullPath)
                    .getConstructor()
                    .newInstance();
        } catch (ClassNotFoundException | InstantiationException |
                 NoSuchMethodException | NullPointerException |
                 InvocationTargetException | IllegalArgumentException |
                 IllegalAccessException exception) {
            _zipCodeValidator = null;
        }

        if (!validateArguments(street, doorNumber, zipCode, city, country)) {
            throw new IllegalArgumentException("Address cannot have empty fields");
        }

        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    private boolean validateArguments(String street, String doorNumber, String zipCode, String city, String country) {
        if (street == null || street.trim().isEmpty()) return false;
        if (doorNumber == null || doorNumber.trim().isEmpty()) return false;
        if (zipCode == null || zipCode.trim().isEmpty()) return false;
        if (city == null || city.trim().isEmpty()) return false;
        if (country == null || country.trim().isEmpty()) return false;

        return _zipCodeValidator != null && _zipCodeValidator.validateZipCode(zipCode);
    }
}
