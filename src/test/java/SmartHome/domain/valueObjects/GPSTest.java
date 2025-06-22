package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPSTest
{

    @Test
    void constructorWithValidGPS()
    {
        // Arrange
        double validLatitude = 40.7128;
        double validLongitude = -74.0060;

        // Act
        GPS gps = new GPS(validLatitude, validLongitude);

        // Assert
        assertEquals(validLatitude, gps.latitude);
        assertEquals(validLongitude, gps.longitude);
    }

    @Test
    void constructorWithInvalidLatitude()
    {
        // Arrange
        double latitude = 91;
        double longitude = 180;
        String expected = "Latitude must be between -90 and 90, and Longitude must be between -180 and 180.";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void constructorWithInvalidLongitude()
    {
        // Arrange
        double latitude = 90;
        double longitude = 181;
        String expected = "Latitude must be between -90 and 90, and Longitude must be between -180 and 180.";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void constructorWithInvalidLatitudeAndLongitude()
    {
        // Arrange
        double latitude = 91;
        double longitude = 181;
        String expected = "Latitude must be between -90 and 90, and Longitude must be between -180 and 180.";

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void validateArgumentsWithInvalidLatitude_upperLimit()
    {
        // Arrange
        double latitude = 91;
        double longitude = 170;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals("Latitude must be between -90 and 90, and Longitude must be between -180 and 180.", actual);
    }

    @Test
    void validateArgumentsWithInvalidLatitude_lowerLimit()
    {
        // Arrange
        double latitude = -91;
        double longitude = 170;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals("Latitude must be between -90 and 90, and Longitude must be between -180 and 180.", actual);
    }

    @Test
    void validateArgumentsWithValidLatitude_lowerLimit()
    {
        // Arrange
        double latitude = -90;
        double longitude = 170;

        // Act
        GPS gps = new GPS(latitude, longitude);

        // Assert
        assertEquals(latitude, gps.latitude);
        assertEquals(longitude, gps.longitude);
    }

    @Test
    void validateArgumentsWithInvalidLongitude_upperLimit()
    {
        // Arrange
        double latitude = 60;
        double longitude = 181;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals("Latitude must be between -90 and 90, and Longitude must be between -180 and 180.", actual);
    }

    @Test
    void validateArgumentsWithInvalidLongitude_lowerLimit()
    {
        // Arrange
        double latitude = 60;
        double longitude = -181;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals("Latitude must be between -90 and 90, and Longitude must be between -180 and 180.", actual);
    }

    @Test
    void validateArgumentsWithValidLongitude_lowerLimit()
    {
        // Arrange
        double latitude = 60;
        double longitude = -180;

        // Act
        GPS gps = new GPS(latitude, longitude);

        // Assert
        assertEquals(latitude, gps.latitude);
        assertEquals(longitude, gps.longitude);
    }

    @Test
    void validateArgumentsWithInvalidGPS_DoubleIsNaN()
    {
        // Arrange
        double latitude = Double.NaN;
        double longitude = 180;

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new GPS(latitude, longitude));
        String actual = exception.getMessage();

        // Assert
        assertEquals("Latitude must be between -90 and 90, and Longitude must be between -180 and 180.", actual);
    }

    @Test
    void sameObjectEqualsTrue()
    {
        // Arrange
        double latitude = 90;
        double longitude = 180;
        GPS newGPS = new GPS(latitude, longitude);

        // Act
        boolean actual = newGPS.equals(newGPS);

        // Assert
        assertTrue(actual);
    }

    @Test
    void objectWithTheSameGPSEqualsTrue()
    {
        // Arrange
        double latitude = 90;
        double longitude = 180;
        GPS newGPS = new GPS(latitude, longitude);
        GPS newGPS2 = new GPS(latitude, longitude);

        // Act
        boolean actual = newGPS.equals(newGPS2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void shouldBeTheSameHashCode()
    {

        // Arrange
        GPS gps1 = new GPS(90, 180);
        GPS gps2 = new GPS(90, 180);

        // Act
        int hashCode1 = gps1.hashCode();
        int hashCode2 = gps2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void equals_NullObject()
    {
        // Arrange
        double latitude = 40.7128;
        double longitude = -74.0060;
        GPS gps = new GPS(latitude, longitude);

        // Act
        boolean result = gps.equals(null);

        // Assert
        assertFalse(result);
    }

}
