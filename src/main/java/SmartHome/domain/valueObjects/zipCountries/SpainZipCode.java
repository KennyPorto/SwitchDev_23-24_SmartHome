package SmartHome.domain.valueObjects.zipCountries;

public class SpainZipCode implements ZipCodeValidator
{

    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("^\\d{5}$");
    }
}
