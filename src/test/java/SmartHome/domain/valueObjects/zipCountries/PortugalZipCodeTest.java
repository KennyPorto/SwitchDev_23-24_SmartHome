package SmartHome.domain.valueObjects.zipCountries;

import SmartHome.domain.valueObjects.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortugalZipCodeTest
{
    @Test
    void validPortugalZipCode()
    {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String country = "Portugal";

        // Act
        Address address = new Address(street, doorNumber, zip, city, country);

        // Assert
        assertNotNull(address);
    }

    @Test
    void invalidZipCodeFormat()
    {
        // Arrange
        String street = "Calle de Oro";
        String doorNumber = "123";
        String zip = "4000000";
        String city = "Porto";
        String country = "Portugal";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, country));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains("Address cannot have empty fields"));
    }

    @Test
    void validatePortugalZipCode()
    {
        // Arrange
        PortugalZipCode validator = new PortugalZipCode();

        // Act
        boolean validZipCode = validator.validateZipCode("4000-000");
        boolean invalidZipCode = validator.validateZipCode("4000000");

        // Assert
        assertTrue(validZipCode);
        assertFalse(invalidZipCode);
    }
}
