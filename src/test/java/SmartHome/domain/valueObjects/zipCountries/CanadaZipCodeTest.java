package SmartHome.domain.valueObjects.zipCountries;

import SmartHome.domain.valueObjects.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CanadaZipCodeTest
{
    @Test
    void validCanadaZipCode()
    {
        // Arrange
        String street = "123 Main St";
        String doorNumber = "123";
        String zip = "A1A 1A1";
        String city = "Toronto";
        String country = "Canada";

        // Act
        Address address = new Address(street, doorNumber, zip, city, country);

        // Assert
        assertNotNull(address);
    }

    @Test
    void invalidCanadaZipCode()
    {
        // Arrange
        String street = "123 Main St";
        String doorNumber = "123";
        String zip = "12345";
        String city = "Toronto";
        String country = "Canada";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, country));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains("Address cannot have empty fields"));
    }
}
