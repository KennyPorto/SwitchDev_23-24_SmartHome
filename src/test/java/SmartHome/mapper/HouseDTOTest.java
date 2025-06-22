package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseDTOTest {

    @Test
    void constructorWithValidParameters() {
        // Act
        HouseDTO houseDTO = new HouseDTO(1, "Rua do Ouro", "123", "1234-567",
                "Porto", "Portugal", 41.14961, -8.61099);

        // Assert
        assertEquals(1, houseDTO.houseId);
        assertEquals ("Rua do Ouro", houseDTO.street);
        assertEquals ( "123", houseDTO.doorNumber);
        assertEquals("1234-567", houseDTO.zipCode);
        assertEquals ("Porto", houseDTO.city);
        assertEquals ("Portugal", houseDTO.country);
        assertEquals ( 41.14961, houseDTO.latitude);
        assertEquals ( -8.61099, houseDTO.longitude);
    }

}

