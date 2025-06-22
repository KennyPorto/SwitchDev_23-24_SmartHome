package SmartHome.persistence.mem;

import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.implementation.TemperatureSensor;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.DeviceID;
import SmartHome.domain.valueObjects.SensorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorRepositoryImplMemTest
{
    private SensorRepositoryImplMem sensorRepository;
    private TemperatureSensor sensorMock;
    private SensorId idMock;

    @BeforeEach
    void setUp() {
        sensorRepository = new SensorRepositoryImplMem();
        idMock = mock(SensorId.class);
        sensorMock = mock(TemperatureSensor.class);
    }

    @Test
    void validRepositoryCreationFromPreConfiguredCatalogue() {
        // Assert
        assertNotNull(sensorRepository);
    }

    @Test
    void validSaveShouldStoreSensor() {
        // Act
        Sensor savedSensor = sensorRepository.save(sensorMock);
        Sensor repositorySensor = sensorRepository.findAll().iterator().next();

        //Assert
        assertEquals(savedSensor, repositorySensor);
    }

    @Test
    void nullSensorShouldNotBeSaved() {
        //Assert
        assertNull(sensorRepository.save(null));
    }

    @Test
    void foundValidSensor() {
        //Arrange
        when(sensorMock.identity()).thenReturn(idMock);
        Sensor savedSensor = sensorRepository.save(sensorMock);

        // Act
        Optional<Sensor> foundSensor = sensorRepository.findById(idMock);

        // Assert
        assertTrue(foundSensor.isPresent());
        assertEquals(savedSensor, foundSensor.orElse(null));
    }

    @Test
    void validSensorExists() {
        //Arrange
        when(sensorMock.identity()).thenReturn(idMock);
        sensorRepository.save(sensorMock);

        // Act
        boolean foundSensor = sensorRepository.existsById(idMock);

        // Assert
        assertTrue(foundSensor);
    }

    @Test
    void notFoundSensor() {
        // Act
        Optional<Sensor> foundSensor = sensorRepository.findById(idMock);

        // Assert
        assertFalse(foundSensor.isPresent());
    }

    @Test
    void invalidSensorDoesNotExist() {
        // Act
        boolean foundSensor = sensorRepository.existsById(idMock);

        // Assert
        assertFalse(foundSensor);
    }

    @Test
    void validFindAllSavedSensors() {
        // Arrange
        TemperatureSensor tempSensorMock = mock(TemperatureSensor.class);
        SensorId tempIdMock = mock(SensorId.class);

        when(tempSensorMock.identity()).thenReturn(tempIdMock);
        when(sensorMock.identity()).thenReturn(idMock);

        sensorRepository.save(sensorMock);
        sensorRepository.save(tempSensorMock);

        // Act
        List<Sensor> savedSensor = sensorRepository.findAll();

        // Assert
        assertTrue(savedSensor.contains(sensorMock));
    }

    @Test
    void validNoSensorsSaved() {
        // Arrange
        RangeActuatorDecimal rangeActuatorMock = mock(RangeActuatorDecimal.class);
        ActuatorId rangeIdMock = mock(ActuatorId.class);

        when(rangeActuatorMock.identity()).thenReturn(rangeIdMock);
        when(sensorMock.identity()).thenReturn(idMock);


        // Act
        List<Sensor> savedSensor = sensorRepository.findAll();

        // Assert
        assertTrue(savedSensor.isEmpty());
    }

    @Test
    void validFindAllIsNotNull() {
        //Arrange
        sensorRepository.save(sensorMock);

        //Act
        Iterable<Sensor> allSensors = sensorRepository.findAll();

        //Assert
        assertNotNull(allSensors);
    }

    @Test
    void findAllByDeviceId() {
        // Arrange
        long devId = 1L;
        DeviceID deviceIDmock1 = new DeviceID(devId);
        when(sensorMock.getDeviceID()).thenReturn(deviceIDmock1);
        sensorRepository.save(sensorMock);

        // Act
        Iterable<Sensor> result = sensorRepository.findAllByDeviceId(devId);
        Iterable<Sensor> expected = List.of(sensorMock);

        // Assert
        assertEquals(expected, result);
    }
}