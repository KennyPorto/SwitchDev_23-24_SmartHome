package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorDTOTest {
    @Test
    void validSensorDTO() {
        SensorDTO sensorDTO = new SensorDTO("SunSensor", "SunsetSensor", 1, 1, "SunsetSensor");

        assertEquals("SunSensor", sensorDTO.name);
        assertEquals("SunsetSensor", sensorDTO.model);
        assertEquals(1, sensorDTO.sensorId);
        assertEquals(1, sensorDTO.deviceId);
        assertEquals("SunsetSensor", sensorDTO.sensorType);
    }
}
