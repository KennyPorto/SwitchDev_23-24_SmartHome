package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AddressDTOTest {
    @Test
    void validConstructor() {
        // Arrange
        String street = "Rua do Ouro";
        String doorNumber = "123";
        String zipCode = "4000-000";
        String city = "Porto";
        String country = "Portugal";

        // Act
        AddressDTO addressDTO = new AddressDTO(street, doorNumber, zipCode, city, country);

        // Assert
        assertEquals(street, addressDTO.street);
        assertEquals(doorNumber, addressDTO.doorNumber);
        assertEquals(zipCode, addressDTO.zipCode);
        assertEquals(city, addressDTO.city);
        assertEquals(country, addressDTO.country);
    }

    @Test
    void constructorWithNullValues() {
        // Arrange

        // Act
        AddressDTO addressDTO = new AddressDTO(null, null, null, null, null);

        // Assert
        assertNull(addressDTO.street);
        assertNull(addressDTO.doorNumber);
        assertNull(addressDTO.zipCode);
        assertNull(addressDTO.city);
        assertNull(addressDTO.country);
    }

    @Test
    void constructorWithEmptyStrings() {
        // Arrange
        String street = "";
        String doorNumber = "";
        String zipCode = "";
        String city = "";
        String country = "";

        // Act
        AddressDTO addressDTO = new AddressDTO(street, doorNumber, zipCode, city, country);

        // Assert
        assertEquals("", addressDTO.street);
        assertEquals("", addressDTO.doorNumber);
        assertEquals("", addressDTO.zipCode);
        assertEquals("", addressDTO.city);
        assertEquals("", addressDTO.country);
    }
}
