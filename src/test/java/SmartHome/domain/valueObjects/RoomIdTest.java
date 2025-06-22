package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomIdTest {


    @Test
    void constructorWithValidId() {
        // Arrange
        long validId = 1;

        // Act
        RoomId roomId = new RoomId(validId);

        // Assert
        assertDoesNotThrow(() -> new RoomId(validId));
        assertEquals(validId, roomId.id);
    }

    @Test
    void constructorWithIdEqualsToZero() {
        // Arrange
        long validId = 0;

        // Act
        RoomId roomId = new RoomId(validId);

        // Assert
        assertDoesNotThrow(() -> new RoomId(validId));
        assertEquals(validId, roomId.id);
    }

    @Test
    void constructorWithNegativeId() {
        // Arrange
        long id = -3;
        String expected = "Invalid arguments passed to the constructor";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoomId(id));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void sameObjectEqualsTrue() {
        // Arrange
        long id = 15;
        RoomId newRoomId = new RoomId(id);

        // Act
        boolean actual = newRoomId.equals(newRoomId);

        // Assert
        assertTrue(actual);
    }

    @Test
    void differentObjectEqualsFalse() {
        // Arrange
        long id = 15;
        RoomId newRoomId = new RoomId(id);
        int differentObject = 1;

        // Act and Assert
        assertNotEquals(newRoomId, differentObject);
    }

    @Test
    void objectWithTheSameIdEqualsTrue() {
        // Arrange
        long id = 23;
        RoomId newRoomId = new RoomId(id);
        RoomId newRoomId2 = new RoomId(id);

        // Act
        boolean actual = newRoomId.equals(newRoomId2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void sameTypeObjectWithDifferentIdEqualsFalse() {
        // Arrange
        long id = 24;
        long id2 = 27;
        RoomId newRoomId = new RoomId(id);
        RoomId newRoomId2 = new RoomId(id2);

        // Act
        boolean actual = newRoomId.equals(newRoomId2);

        // Assert
        assertFalse(actual);
    }

    @Test
    void sameObjectHashCode() {
        // Arrange
        long roomId = 3;
        RoomId myRoomId = new RoomId(roomId);

        // Act
        int result = myRoomId.hashCode();
        int result2 = myRoomId.hashCode();

        // Assert
        assertEquals(result, result2);
    }

    @Test
    void differentObjectHashCode() {
        // Arrange
        long roomId = 3;
        long roomId2 = 5;

        RoomId myRoomId = new RoomId(roomId);
        RoomId myRoomId2 = new RoomId(roomId2);

        // Act
        int result = myRoomId.hashCode();
        int result2 = myRoomId2.hashCode();

        // Assert
        assertNotEquals(result, result2);
    }
}
