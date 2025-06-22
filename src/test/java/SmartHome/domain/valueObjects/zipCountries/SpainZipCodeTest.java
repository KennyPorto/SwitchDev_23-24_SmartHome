package SmartHome.domain.valueObjects.zipCountries;

import SmartHome.domain.valueObjects.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpainZipCodeTest
{
    @Test
    void validSpainZipCode()
    {
        // Arrange
        String street = "Calle de Oro";
        String doorNumber = "123";
        String zip = "40000";
        String city = "Porto";
        String country = "Spain";

        // Act
        Address address = new Address(street, doorNumber, zip, city, country);

        // Assert
        assertNotNull(address);
    }

    @Test
    void invalidSpainZipCode()
    {
        // Arrange
        String street = "Calle de Oro";
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String country = "Spain";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, country));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains("Address cannot have empty fields"));
    }

}
