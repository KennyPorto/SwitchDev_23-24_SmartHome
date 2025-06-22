package SmartHome.domain.valueObjects.zipCountries;

public class CanadaZipCode implements ZipCodeValidator
{

    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$");
    }
}
