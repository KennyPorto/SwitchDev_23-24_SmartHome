package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class HouseIdTest {

    @Test
    void constructorWithValidId() {
        // Arrange
        long validId = 123456;

        // Act
        HouseId houseId = new HouseId(validId);

        // Assert
        assertEquals(validId, houseId.id);
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, Long.MIN_VALUE})
    void invalidNegativeDeviceID(long value) {
        // Arrange
        String expectedMessage = "Invalid arguments passed to the constructor";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new HouseId(value));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void objectWithTheSameIdEqualsTrue() {
        // Arrange
        long id = 23;
        HouseId newHouseId = new HouseId(id);
        HouseId newHouseId2 = new HouseId(id);

        // Act
        boolean actual = newHouseId.equals(newHouseId2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void sameTypeObjectWithDifferentIdEqualsFalse() {
        // Arrange
        long id = 24;
        long id2 = 27;
        HouseId newHouseId = new HouseId(id);
        HouseId newHouseId2 = new HouseId(id2);

        // Act
        boolean actual = newHouseId.equals(newHouseId2);

        // Assert
        assertFalse(actual);
    }

    @Test
    void differentTypeObjectEqualsFalse() {
        // Arrange
        long id = 25;
        HouseId newHouseId = new HouseId(id);
        Object object = new Object();

        // Act
        boolean actual = newHouseId.equals(object);

        // Assert
        assertFalse(actual);
    }

    @Test
    void sameHashCode() {
        // Arrange
        long id = 26;
        HouseId newHouseId = new HouseId(id);
        HouseId newHouseId2 = new HouseId(id);

        // Act
        int hashCode1 = newHouseId.hashCode();
        int hashCode2 = newHouseId2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void differentHashCode() {
        // Arrange
        long id = 26;
        long id2 = 27;
        HouseId newHouseId = new HouseId(id);
        HouseId newHouseId2 = new HouseId(id2);

        // Act
        int hashCode1 = newHouseId.hashCode();
        int hashCode2 = newHouseId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

}
