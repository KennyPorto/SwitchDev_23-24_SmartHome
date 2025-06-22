package SmartHome.domain.valueObjects.zipCountries;

public class PortugalZipCode implements ZipCodeValidator
{

    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("\\d{4}-\\d{3}");
    }
}
