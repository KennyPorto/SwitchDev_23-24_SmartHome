package SmartHome.domain.device;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    @Test
    public void createValidLog() {
        // Arrange
        LocalDateTime time = LocalDateTime.now();
        String message = "Device deactivated";
        Log log = new Log(time, message);

        // Act
        String result = log.getLog();

        //Assert
        assertTrue(result.contains(message));
    }

    @Test
    public void createLogNullMessage() {
        // Arrange
        LocalDateTime time = LocalDateTime.now();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Log(time, null));
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    public void createLogEmptyMessage() {
        // Arrange
        LocalDateTime time = LocalDateTime.now();
        String message = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Log(time, message));
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    void createLogNullTime() {
        // Arrange
        String message = "Test message";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Log(null, message));
        assertEquals("Time cannot be null", exception.getMessage());
    }
}