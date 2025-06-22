package SmartHome.mapper;

import SmartHome.domain.activitylog.ActivityLog;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.ActuatorActivityLogDataModel;
import SmartHome.persistence.jpa.datamodel.SensorActivityLogDataModel;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivityLogMapperTest
{

    @Test
    void singleActuatorActivityLogToDomain()
    {
        // Arrange
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(new ActivityLogId(1), new ActuatorId(1),
                new TimeStamp(ZonedDateTime.now()), new Measurement("80 %"));
        ActuatorActivityLogDataModel dataModel = mock(ActuatorActivityLogDataModel.class);
        when(dataModel.getActivityLogId()).thenReturn(1L);
        when(dataModel.getActuatorId()).thenReturn(1L);
        when(dataModel.getTimeStamp()).thenReturn(ZonedDateTime.now());
        when(dataModel.getMeasurement()).thenReturn("80 %");

        // Act
        ActivityLog result = ActivityLogMapper.toDomain(dataModel);

        // Assert
        assertEquals(actuatorActivityLog.identity().id, result.identity().id);
        assertEquals(actuatorActivityLog.getMeasurement().measurement, result.getMeasurement().measurement);
    }

    @Test
    void multipleActuatorActivityLogToDomain()
    {
        // Arrange

        ActuatorActivityLog actuatorActivityLog1 = new ActuatorActivityLog(new ActivityLogId(1), new ActuatorId(1),
                new TimeStamp(ZonedDateTime.now()), new Measurement("80 %"));
        ActuatorActivityLogDataModel dataModel1 = mock(ActuatorActivityLogDataModel.class);
        when(dataModel1.getActivityLogId()).thenReturn(1L);
        when(dataModel1.getActuatorId()).thenReturn(1L);
        when(dataModel1.getTimeStamp()).thenReturn(ZonedDateTime.now());
        when(dataModel1.getMeasurement()).thenReturn("80 %");

        ActuatorActivityLogDataModel dataModel2 = mock(ActuatorActivityLogDataModel.class);
        when(dataModel2.getActivityLogId()).thenReturn(2L);
        when(dataModel2.getActuatorId()).thenReturn(2L);
        when(dataModel2.getTimeStamp()).thenReturn(ZonedDateTime.now());
        when(dataModel2.getMeasurement()).thenReturn("90 %");

        // Act
        Iterable<ActuatorActivityLog> result = ActivityLogMapper.toDomain(List.of(dataModel1, dataModel2));

        // Assert
        assertEquals(actuatorActivityLog1.identity().id, result.iterator().next().identity().id);
        assertEquals(actuatorActivityLog1.getMeasurement().measurement, result.iterator().next().getMeasurement().measurement);
    }

    @Test
    void singleSensorActivityLogToDomain()
    {
        // Arrange
        SensorActivityLog sensorActivityLog = new SensorActivityLog(new ActivityLogId(1),
              new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("80 %"));
        SensorActivityLogDataModel dataModel = mock(SensorActivityLogDataModel.class);
        when(dataModel.getSensorActivityLogId()).thenReturn(1L);
        when(dataModel.getSensorId()).thenReturn(1L);
        when(dataModel.getTimeStamp()).thenReturn(ZonedDateTime.now());
        when(dataModel.getMeasurement()).thenReturn("80 %");

        // Act
        ActivityLog result = ActivityLogMapper.sensorDataModelToDomain(dataModel);

        // Assert
        assertEquals(sensorActivityLog.identity().id, result.identity().id);
        assertEquals(sensorActivityLog.getMeasurement().measurement, result.getMeasurement().measurement);
    }

    @Test
    void multipleSensorActivityLogToDomain()
    {
        // Arrange

        SensorActivityLog sensorActivityLog1 = new SensorActivityLog(new ActivityLogId(1),
              new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("80"));
        SensorActivityLogDataModel dataModel1 = mock(SensorActivityLogDataModel.class);
        when(dataModel1.getSensorActivityLogId()).thenReturn(1L);
        when(dataModel1.getSensorId()).thenReturn(1L);
        when(dataModel1.getTimeStamp()).thenReturn(ZonedDateTime.now());
        when(dataModel1.getMeasurement()).thenReturn("80");

        SensorActivityLogDataModel dataModel2 = mock(SensorActivityLogDataModel.class);
        when(dataModel2.getSensorActivityLogId()).thenReturn(2L);
        when(dataModel2.getSensorId()).thenReturn(2L);
        when(dataModel2.getTimeStamp()).thenReturn(ZonedDateTime.now());
        when(dataModel2.getMeasurement()).thenReturn("90");

        // Act
        Iterable<SensorActivityLog> result = ActivityLogMapper.sensorsDataModelToDomain(List.of(dataModel1, dataModel2));

        // Assert
        assertEquals(sensorActivityLog1.identity().id, result.iterator().next().identity().id);
        assertEquals(sensorActivityLog1.getMeasurement().measurement, result.iterator().next().getMeasurement().measurement);
    }

    @Test
    void singleSensorActivityLogToDTO()
    {
        // Arrange
        SensorActivityLog sensorActivityLog = new SensorActivityLog(new ActivityLogId(1),
              new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("80 %"));
        SensorActivityLogDTO sensorActivityLogDTO = new SensorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), "80 %");

        // Act
        SensorActivityLogDTO result = ActivityLogMapper.sensorToDTO(sensorActivityLog);

        // Assert
        assertEquals(sensorActivityLogDTO.measurement, result.measurement);
    }

    @Test
    void multipleSensorActivityLogToDTO()
    {
        // Arrange

        SensorActivityLog sensorActivityLog1 = new SensorActivityLog(new ActivityLogId(1),
              new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("80 %"));
        SensorActivityLogDTO sensorActivityLogDTO1 = new SensorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), "80 %");

        SensorActivityLog sensorActivityLog2 = new SensorActivityLog(new ActivityLogId(2),
              new TimeStamp(ZonedDateTime.now()), new SensorId(2), new Measurement("90 %"));

        // Act
        Iterable<SensorActivityLogDTO> result = ActivityLogMapper.sensorsToDTO(List.of(sensorActivityLog1, sensorActivityLog2));

        // Assert
        assertEquals(sensorActivityLogDTO1.measurement, result.iterator().next().measurement);
    }

    @Test
    void sensorLogAndMeasurementToDto()
    {
        //Arrange
        List<SensorActivityLog> data = new ArrayList<>();
        SensorActivityLog activityLog = new SensorActivityLog(new ActivityLogId(1), new TimeStamp(ZonedDateTime.now()), new SensorId(1), new Measurement("measurement"));
        data.add(activityLog);
        SensorActivityLogDTO sensorActivityLogDTO = new SensorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), "measurement");
        List<SensorActivityLogDTO> expected = new ArrayList<>();
        expected.add(sensorActivityLogDTO);

        //Act
        List<SensorActivityLogDTO> response = ActivityLogMapper.sensorLogAndMeasurementToDto(data);

        //Assert
        assertEquals(expected.get(0).measurement, response.get(0).measurement);
    }

    @Test
    void singleActuatorActivityLogToDTO()
    {
        // Arrange
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(new ActivityLogId(1), new ActuatorId(1),
                new TimeStamp(ZonedDateTime.now()), new Measurement("80 %"));
        ActuatorActivityLogDTO actuatorActivityLogDTO = new ActuatorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), "80 %");

        // Act
        ActuatorActivityLogDTO result = ActivityLogMapper.toDTO(actuatorActivityLog);

        // Assert
        assertEquals(actuatorActivityLogDTO.measurement, result.measurement);
    }

    @Test
    void multipleActuatorActivityLogToDTO()
    {
        // Arrange

        ActuatorActivityLog actuatorActivityLog1 = new ActuatorActivityLog(new ActivityLogId(1), new ActuatorId(1),
                new TimeStamp(ZonedDateTime.now()), new Measurement("80 %"));
        ActuatorActivityLogDTO actuatorActivityLogDTO1 = new ActuatorActivityLogDTO(1, 1,
                ZonedDateTime.now().toString(), "80 %");

        ActuatorActivityLog actuatorActivityLog2 = new ActuatorActivityLog(new ActivityLogId(2), new ActuatorId(2),
                new TimeStamp(ZonedDateTime.now()), new Measurement("90 %"));

        // Act
        Iterable<ActuatorActivityLogDTO> result = ActivityLogMapper.toDTOs(List.of(actuatorActivityLog1,
                actuatorActivityLog2));

        // Assert
        assertEquals(actuatorActivityLogDTO1.measurement, result.iterator().next().measurement);
    }

    @Test
    void actuatorLogAndMeasurementToDto()
    {
        // Arrange
        ActuatorActivityLog actuatorActivityLog1 = new ActuatorActivityLog(new ActivityLogId(1), new ActuatorId(1),
                new TimeStamp(ZonedDateTime.now()), new Measurement("80 %"));
        ActuatorActivityLog actuatorActivityLog2 = new ActuatorActivityLog(new ActivityLogId(2), new ActuatorId(2),
                new TimeStamp(ZonedDateTime.now()), new Measurement("90 %"));
        List<ActuatorActivityLog> actuatorActivityLogs = List.of(actuatorActivityLog1, actuatorActivityLog2);

        // Act
        List<ActuatorActivityLogDTO> result = ActivityLogMapper.actuatorLogAndMeasurementToDto(actuatorActivityLogs);

        // Assert
        assertEquals(actuatorActivityLogs.size(), result.size());
        for (int i = 0; i < actuatorActivityLogs.size(); i++) {
            assertEquals(actuatorActivityLogs.get(i).getMeasurement().measurement, result.get(i).measurement);
        }
    }

}