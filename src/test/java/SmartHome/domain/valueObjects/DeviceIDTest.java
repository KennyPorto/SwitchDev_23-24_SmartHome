package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceIDTest {

    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, Long.MAX_VALUE})
    void validPositiveDeviceID(long value) {
        //Arrange
        DeviceID deviceID = new DeviceID(value);

        // Act
        String id = String.valueOf(deviceID.id);

        // Assert
        assertEquals(String.valueOf(value), id);
    }

    @ParameterizedTest
    @ValueSource(longs = {- 1L, Long.MIN_VALUE})
    void invalidNegativeDeviceID(long value) {
        // Arrange
        String expectedMessage = "Device ID cannot be smaller than 0";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new DeviceID(value));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameObject() {
        // Arrange
        DeviceID DeviceID = new DeviceID(1L);

        // Act & Assert
        assertEquals(DeviceID, DeviceID);
    }

    @Test
    void equalsSameID() {
        // Arrange
        DeviceID DeviceID = new DeviceID(1L);
        DeviceID sameDeviceID = new DeviceID(1L);

        // Act & Assert
        assertEquals(DeviceID, sameDeviceID);
    }

    @Test
    void notEqualsDifferentID() {
        // Arrange
        DeviceID DeviceID = new DeviceID(1L);
        DeviceID differentDeviceID = new DeviceID(2L);

        // Act & Assert
        assertNotEquals(DeviceID, differentDeviceID);
    }

    @Test
    void notEqualsDifferentObject() {
        // Arrange
        DeviceID DeviceID = new DeviceID(1L);
        DeviceName DeviceName = new DeviceName("Heater");

        // Act & Assert
        assertNotEquals(DeviceID, DeviceName);
    }

    @Test
    void shouldBeTheSameHashCode() {

        // Arrange
        DeviceID deviceId1 = new DeviceID(1L);
        DeviceID deviceId2 = new DeviceID(1L);

        // Act
        int hashCode1 = deviceId1.hashCode();
        int hashCode2 = deviceId2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void shouldBeDifferentHashCode() {

        // Arrange
        DeviceID deviceId1 = new DeviceID(1L);
        DeviceID deviceId2 = new DeviceID(2L);

        // Act
        int hashCode1 = deviceId1.hashCode();
        int hashCode2 = deviceId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    void getDeviceId() {
        DeviceID deviceID = new DeviceID(1L);

        assertEquals(1, deviceID.getId());
    }
}
