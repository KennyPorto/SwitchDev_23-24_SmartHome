package SmartHome.mapper;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceRecordsDTOTest {
    @Test
    void createValidDto() {
        // Arrange
        int listIndex = 0;
        String measurement = "2.0";
        String measurement1 = "1.0";
        ActuatorActivityLogDTO actuatorActivityLogDTO = new ActuatorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), measurement);
        SensorActivityLogDTO sensorActivityLogDTO = new SensorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), measurement1);

        List<ActuatorActivityLogDTO> actuatorActivityLogDTOS = List.of(actuatorActivityLogDTO);
        List<SensorActivityLogDTO> sensorActivityLogDTOS = List.of(sensorActivityLogDTO);

        DeviceRecordsDTO deviceRecordsDTO = new DeviceRecordsDTO(actuatorActivityLogDTOS, sensorActivityLogDTOS);

        // Assert
        assertEquals(actuatorActivityLogDTOS.get(listIndex).measurement,
                deviceRecordsDTO.actuatorLogs.iterator().next().measurement);
    }
}