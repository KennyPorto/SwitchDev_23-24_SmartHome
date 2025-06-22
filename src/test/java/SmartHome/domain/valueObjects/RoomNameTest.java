package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RoomNameTest {
    @Test
    void constructorWithValidName() {
        // Arrange
        String name = "Living Room";
        RoomName newRoomName = new RoomName(name);
        String expected = "Living Room";

        //Act
        String result = newRoomName.name;

        //Assert
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @NullAndEmptySource
    void constructorWithInvalidParameters(String name) {
        //Arrange
        String expectedMessage = "Room name cannot be empty";

        //Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new RoomName(name));
        String actualMessage = exception.getMessage();

        //Assert
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void equalsSameObject() {
        //Arrange
        String name = "Living Room";
        RoomName newRoomName = new RoomName(name);

        //Act
        boolean result = newRoomName.equals(newRoomName);

        //Assert
        assertTrue(result);
    }

    @Test
    void differentObjectEqualsFalse() {
        //Arrange
        String name = "Living Room";
        RoomName newRoomName = new RoomName(name);
        int differentObject = 1;

        //Act and Assert
        assertNotEquals(newRoomName, differentObject);
    }

    @Test
    void equalsSameObjectName() {
        //Arrange
        String name = "Living Room";
        RoomName newRoomName = new RoomName(name);
        RoomName newRoomName2 = new RoomName(name);

        //Act
        boolean result = newRoomName.equals(newRoomName2);

        //Assert
        assertTrue(result);
    }

    @Test
    void sameObjectHashCode() {
        // Arrange

        RoomName roomName = new RoomName("Living Room");
        RoomName roomName2 = new RoomName("Living Room");

        // Act
        int result = roomName.hashCode();
        int result2 = roomName2.hashCode();

        // Assert
        assertEquals(result, result2);
    }

}
