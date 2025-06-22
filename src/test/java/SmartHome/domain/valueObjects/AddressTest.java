package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void validEquals() {
        // Arrange
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");

        // Act
        boolean result = address1.equals(address2);

        // Assert
        assertTrue(result);
    }

    @Test
    void validConstructor() {
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
    void invalidConstructor() {
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
    void validPortugalZipCode() {
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
    void validSpainZipCode() {
        // Arrange
        String street = "Calle de Oro";
        String doorNumber = "123";
        String zip = "28000";
        String city = "Madrid";
        String country = "Spain";

        // Act
        Address address = new Address(street, doorNumber, zip, city, country);

        // Assert
        assertNotNull(address);
    }

    @Test
    void validCanadaZipCode() {
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
    void validUSAZipCode() {
        // Arrange
        String street = "123 Main St";
        String doorNumber = "123";
        String zip = "12345";
        String city = "New York";
        String country = "USA";

        // Act
        Address address = new Address(street, doorNumber, zip, city, country);

        // Assert
        assertNotNull(address);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Brazil", "France", "Germany"})
    void invalidCountry(String country) {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, country));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void invalidCountryZipCode() {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String country = "Spain";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, country));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionWhenNullStreet() {
        // Arrange
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(null, doorNumber, zip, city, country));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionWhenNullDoorNumber() {
        // Arrange
        String street = "Rua do Ouro";
        String zip = "4000-000";
        String city = "Porto";
        String country = "Portugal";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(street, null, zip, city, country));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionWhenNullZip() {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String city = "Porto";
        String country = "Portugal";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, null, city, country));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionWhenNullCity() {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zip = "4000-000";
        String country = "Portugal";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, null, country));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void throwExceptionWhenNullCountry() {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String expected = "Address cannot have empty fields";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, null));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void validZipCodeValidator() {
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
    void invalidZipCodeValidator() {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zip = "4000-000";
        String city = "Porto";
        String country = "França";

        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(street, doorNumber, zip, city, country));
    }

    @Test
    void addressDoesNotEqualObjectOfDifferentType() {
        Address address = new Address("Street", "123", "4000-000", "Coimbra", "Portugal");
        Object other = new Object();
        assertNotEquals(address, other);
    }



    @Test
    void sameHashCode() {
        // Arrange
        Address address1 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");
        Address address2 = new Address("Rua do Ouro", "123", "4000-000", "Porto", "Portugal");

        // Act
        int hash1 = address1.hashCode();
        int hash2 = address2.hashCode();

        // Assert
        assertEquals(hash1, hash2);
    }
}
