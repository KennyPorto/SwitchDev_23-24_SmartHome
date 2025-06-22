package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTypeDTOTest {
    @Test
    void sensorTypeDto() {
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO( "Temperature", "C");
        assertEquals(sensorTypeDTO.id, "Temperature");
        assertEquals(sensorTypeDTO.measurementUnit, "C");
    }

    @Test
    void getSensorTypeId() {
        SensorTypeDTO sensorTypeDto = new SensorTypeDTO( "st1", "Humidity");
        // Arrange
        String expected = "st1";

        // Act
        String result = sensorTypeDto.id;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getSensorTypeMeasurementUnit() {
        SensorTypeDTO sensorTypeDto = new SensorTypeDTO( "st1", "Humidity");
        // Arrange
        String expected = "Humidity";

        // Act
        String result = sensorTypeDto.measurementUnit;

        // Assert
        assertEquals(expected, result);
    }
}