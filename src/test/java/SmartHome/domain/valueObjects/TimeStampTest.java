package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TimeStampTest
{
    @Test
    void validConstructor()
    {
        // Arrange
        ZonedDateTime expectedValue = ZonedDateTime.now();

        // Act
        TimeStamp timeStamp = new TimeStamp(expectedValue);

        // Assert
        assertEquals(expectedValue, timeStamp.value);
    }

    @Test
    void invalidConstructorNull()
    {
        // Arrange
        String expected = "TimeStamp cannot be null.";
        ZonedDateTime value = null;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new TimeStamp(value));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void invalidConstructor()
    {
        // Arrange
        ZonedDateTime fixedDateTime1 = ZonedDateTime.parse("2023-01-01T00:00:00Z");
        TimeStamp time = new TimeStamp(fixedDateTime1);
        ZonedDateTime fixedDateTime2 = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        TimeStamp time2 = new TimeStamp(fixedDateTime2);

        // Act & Assert
        assertNotEquals(time.value, time2.value);
    }

    @Test
    void validEquals()
    {
        // Arrange
        TimeStamp time = new TimeStamp(ZonedDateTime.now());

        // Act
        boolean result = time.equals(time);

        // Assert
        assertTrue(result);
    }

    @Test
    void invalidEquals()
    {
        // Arrange
        TimeStamp time = new TimeStamp(ZonedDateTime.now());
        TimeStamp time2 = new TimeStamp(ZonedDateTime.now());

        // Act
        boolean result = time.equals(time2);

        // Assert
        assertFalse(result);
    }

    @Test
    void invalidEqualsNull()
    {
        // Arrange
        TimeStamp time = new TimeStamp(ZonedDateTime.now());

        // Act
        boolean result = time.equals(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void invalidEqualsDifferentObject()
    {
        // Arrange
        TimeStamp time = new TimeStamp(ZonedDateTime.now());

        // Act
        boolean result = time.equals(new Object());

        // Assert
        assertFalse(result);
    }

    @Test
    void equalsSameValue()
    {
        // Arrange
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        TimeStamp time1 = new TimeStamp(fixedDateTime);
        TimeStamp time2 = new TimeStamp(fixedDateTime);

        // Act
        boolean result = time1.equals(time2);

        // Assert
        assertTrue(result);
    }

    @Test
    void validHashCode()
    {
        // Arrange
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        TimeStamp time = new TimeStamp(fixedDateTime);
        TimeStamp time2 = new TimeStamp(fixedDateTime);

        // Act
        int result = time.hashCode();
        int result2 = time2.hashCode();

        // Assert
        assertEquals(result, result2);
    }

    @Test
    void invalidHashCode()
    {
        // Arrange
        ZonedDateTime fixedDateTime1 = ZonedDateTime.parse("2023-01-01T00:00:00Z");
        TimeStamp time = new TimeStamp(fixedDateTime1);
        ZonedDateTime fixedDateTime2 = ZonedDateTime.parse("2024-01-01T00:00:00Z");
        TimeStamp time2 = new TimeStamp(fixedDateTime2);

        // Act
        int result = time.hashCode();
        int result2 = time2.hashCode();

        // Assert
        assertNotEquals(result, result2);
    }
}
