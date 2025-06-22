package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    @Test
    void constructorWithValidFloor() {
        // Arrange
        String validFloor = "1A";

        // Act
        Floor floor = new Floor(validFloor);

        // Assert
        assertDoesNotThrow(() -> new Floor(validFloor));
        assertEquals(validFloor, floor.floor);
    }

    @Test
    void objectWithTheSameFloorEqualsTrue() {
        // Arrange
        String floor = "FirstFloor";
        Floor newFloor = new Floor(floor);
        Floor newFloor2 = new Floor(floor);

        // Act
        boolean actual = newFloor.equals(newFloor2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange
        String floorName = "FirstFloor";
        Floor floor = new Floor(floorName);

        // Act
        boolean result = floor.equals(floor);

        // Assert
        assertTrue(result);
    }

    @Test
    void differentTypeObjectWithSameFloorEqualsFalse() {
        // Arrange
        String floor = "FirstFloor";
        Floor newFloor = new Floor(floor);
        Object newObject = new Object();

        // Act
        boolean actual = newFloor.equals(newObject);

        // Assert
        assertFalse(actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void constructor_invalidFloorName_throwsIllegalArgumentException(String floorName) {
        assertThrows(IllegalArgumentException.class, () -> new Floor(floorName));
    }

    @Test
    void sameObjectHashCode() {
        // Arrange
        String floor = "Floor1";
        Floor myFloor = new Floor(floor);

        // Act
        int result = myFloor.hashCode();
        int result2 = myFloor.hashCode();

        // Assert
        assertEquals(result, result2);
    }
}
