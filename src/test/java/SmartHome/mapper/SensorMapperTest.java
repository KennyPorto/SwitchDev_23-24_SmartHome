package SmartHome.mapper;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.implementation.SunriseSensor;
import SmartHome.domain.sensors.implementation.SunsetSensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.SensorDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorMapperTest {
    private SensorId sensorId;
    private SensorName sensorName;
    private SunsetSensor sunsetSensor;
    private SensorTypeId sensorTypeId;
    private DeviceID deviceID;
    private SensorModel sensorModel;

    @BeforeEach
    void setup() {
        sensorId = mock(SensorId.class);
        sensorName = mock(SensorName.class);
        deviceID = mock(DeviceID.class);
        sunsetSensor = mock(SunsetSensor.class);
        sensorTypeId = mock(SensorTypeId.class);
        sensorModel = mock(SensorModel.class);

        when(sunsetSensor.identity()).thenReturn(sensorId);
        when(sunsetSensor.getName()).thenReturn(sensorName);
        when(sunsetSensor.getDeviceID()).thenReturn(deviceID);
        when(sunsetSensor.getSensorTypeId()).thenReturn(sensorTypeId);
        when(sunsetSensor.getSensorModel()).thenReturn(sensorModel);

    }

    @Test
    void transformToDTO() {
        // Act
        SensorDTO sensorDTO = SensorMapper.toDTO(sunsetSensor);

        // Assert
        assertEquals(sensorName.name, sensorDTO.name);
        assertEquals(sensorModel.model, sensorDTO.model);
        assertEquals(deviceID.id, sensorDTO.deviceId);
        assertEquals(sensorId.id, sensorDTO.sensorId);
        assertEquals(sensorTypeId.id, sensorDTO.sensorType);
    }

    @Test
    void transformToDTOList() {
        // Arrange
        SunriseSensor sunriseSensor = mock(SunriseSensor.class);
        when(sunriseSensor.identity()).thenReturn(sensorId);
        when(sunriseSensor.getName()).thenReturn(sensorName);
        when(sunriseSensor.getDeviceID()).thenReturn(deviceID);
        when(sunriseSensor.getSensorTypeId()).thenReturn(sensorTypeId);
        when(sunriseSensor.getSensorModel()).thenReturn(sensorModel);

        // Act
        Iterable<SensorDTO> result = SensorMapper.toDTO(
              List.of(sunsetSensor, sunriseSensor)
        );

        Iterable<SensorDTO> expected = List.of(
              SensorMapper.toDTO(sunsetSensor),
              SensorMapper.toDTO(sunriseSensor)
        );

        // Assert
        assertEquals(expected.iterator().next().name, result.iterator().next().name);
        assertEquals(expected.iterator().next().model, result.iterator().next().model);
        assertEquals(expected.iterator().next().deviceId, result.iterator().next().deviceId);
        assertEquals(expected.iterator().next().sensorId, result.iterator().next().sensorId);
        assertEquals(expected.iterator().next().sensorType, result.iterator().next().sensorType);

        assertEquals(2, ((List<SensorDTO>) result).size());
    }

    @Test
    void transformDataModelToDomain()
    {
        // Arrange
        SensorDataModel sensorDataModel = mock(SensorDataModel.class);
        when(sensorDataModel.getId()).thenReturn(Long.valueOf("1"));
        when(sensorDataModel.getSensorName()).thenReturn("Sensor");
        when(sensorDataModel.getDeviceId()).thenReturn(Long.valueOf("2"));
        when(sensorDataModel.getSensorTypeId()).thenReturn("3");
        when(sensorDataModel.getSensorModel()).thenReturn("SunsetSensor");

        // Act
        Sensor result = SensorMapper.dataModelToDomain(sensorDataModel);

        // Assert
        assertEquals(sensorDataModel.getId(), result.identity().id);
        assertEquals(sensorDataModel.getSensorModel(), result.getSensorModel().model);
        assertEquals(sensorDataModel.getSensorName(), result.getName().name);
        assertEquals(sensorDataModel.getDeviceId(), result.getDeviceID().id);
        assertEquals(sensorDataModel.getSensorTypeId(), result.getSensorTypeId().id);
    }

    @Test
    void transformDataModelListToDomain()
    {
        // Arrange
        SensorDataModel sensorDataModel = mock(SensorDataModel.class);
        when(sensorDataModel.getId()).thenReturn(Long.valueOf("1"));
        when(sensorDataModel.getSensorName()).thenReturn("Sensor");
        when(sensorDataModel.getDeviceId()).thenReturn(Long.valueOf("2"));
        when(sensorDataModel.getSensorTypeId()).thenReturn("3");
        when(sensorDataModel.getSensorModel()).thenReturn("SunsetSensor");

        // Act
        Iterable<Sensor> result = SensorMapper.dataModelListToDomain(List.of(sensorDataModel));

        // Assert
        assertEquals(sensorDataModel.getId(), result.iterator().next().identity().id);
        assertEquals(sensorDataModel.getSensorModel(), result.iterator().next().getSensorModel().model);
        assertEquals(sensorDataModel.getSensorName(), result.iterator().next().getName().name);
        assertEquals(sensorDataModel.getDeviceId(), result.iterator().next().getDeviceID().id);
        assertEquals(sensorDataModel.getSensorTypeId(), result.iterator().next().getSensorTypeId().id);
    }
}