package SmartHome.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GpsDTOTest {

    private GpsDTO gpsDTO;

    @BeforeEach
    public void setup() {
        gpsDTO = new GpsDTO(40.7128, 74.0060);
    }

    @Test
    void getValidLatitude() {
        // Arrange
        double expected = 40.7128;

        // Act
        double result = gpsDTO.latitude;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getValidLongitude() {
        // Arrange
        double expected = 74.0060;

        // Act
        double result = gpsDTO.longitude;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void validConstructor() {
        // Arrange
        GpsDTO gps = new GpsDTO(51.5074, 0.1278);

        // Act & Assert
        assertEquals(51.5074, gps.latitude);
        assertEquals(0.1278, gps.longitude);
    }

}
