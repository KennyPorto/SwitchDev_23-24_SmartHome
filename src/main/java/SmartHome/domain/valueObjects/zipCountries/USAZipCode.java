package SmartHome.domain.valueObjects.zipCountries;

public class USAZipCode implements ZipCodeValidator
{

    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("^\\d{5}(-\\d{4})?$");
    }
}
