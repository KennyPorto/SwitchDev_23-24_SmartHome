package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.SunriseValue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SunriseValueTest
{
    @Test
    void calculateSunrise()
    {
        // Arrange
        LocalDate date = LocalDate.of(2024, 4, 10);
        double latitude = 40.7128;
        double longitude = -74.0060;

        // Act
        LocalTime result = SunriseValue.calculateSunrise(date, latitude, longitude);

        //Assert
        assertNotNull(result);
    }


}
