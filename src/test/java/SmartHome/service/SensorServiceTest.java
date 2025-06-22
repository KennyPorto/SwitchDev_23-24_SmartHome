package SmartHome.service;

import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.valueObjects.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorServiceTest {
    private SensorRepository mockSensorRepository;
    private SensorFactory mockSensorFactory;
    private DeviceRepository mockDeviceRepository;
    private DeviceFactory mockDeviceFactory;


    @BeforeEach
    void setup() {
        mockSensorRepository = mock(SensorRepository.class);
        mockSensorFactory = mock(SensorFactory.class);
        mockDeviceRepository = mock(DeviceRepository.class);
        mockDeviceFactory = mock(DeviceFactory.class);
    }

    @Test
    void addSensorWithValidArguments() {
        // Arrange
        SensorName name = mock(SensorName.class);
        SensorModel model = mock(SensorModel.class);
        SensorId id = mock(SensorId.class);
        Sensor sensor = mock(Sensor.class);
        DeviceID deviceId = mock(DeviceID.class);
        SensorTypeId typeName = mock(SensorTypeId.class);

        when(mockSensorRepository.existsById(id)).thenReturn(false);
        when(mockDeviceRepository.existsById(deviceId)).thenReturn(true);
        when(mockSensorFactory.createSensor(deviceId, model, name, id, typeName)).thenReturn(sensor);
        when(mockSensorRepository.save(sensor)).thenReturn(sensor);

        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);

        // Act
        Sensor result = sensorService.add(id, model, deviceId, typeName, name);

        // Assert
        assertEquals(result, sensor);
    }

    @Test
    void addSensorWithInvalidDeviceIdTest() {
        // Arrange
        SensorName name = mock(SensorName.class);
        SensorModel model = mock(SensorModel.class);
        SensorId id = mock(SensorId.class);
        DeviceID deviceId = mock(DeviceID.class);
        SensorTypeId typeName = mock(SensorTypeId.class);

        when(mockDeviceRepository.existsById(deviceId)).thenReturn(false);

        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> sensorService.add(id, model, deviceId, typeName, name)
        );
        String expectedMessage = "Device with this deviceId doesnt exist";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findAll()
    {
        // Arrange
        Sensor sensor = mock(Sensor.class);
        SensorId sensorId = mock(SensorId.class);

        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);

        when(mockSensorRepository.findAll()).thenReturn(List.of(sensor));
        when(sensor.identity()).thenReturn(sensorId);

        // Act
        Iterable<Sensor> result = sensorService.findAll();
        Iterable<Sensor> expected = List.of(sensor);

        // Assert
        assertEquals(expected.iterator().next().identity().id,
                result.iterator().next().identity().id);
    }

    @Test
    void findById()
    {
        // Arrange
        long id = 1L;
        Sensor sensor = mock(Sensor.class);
        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);
        SensorId sensorId = mock(SensorId.class);

        when(mockSensorFactory.createSensorId(id)).thenReturn(Optional.of(sensorId));
        when(mockSensorRepository.findById(sensorId)).thenReturn(Optional.of(sensor));

        Sensor result = sensorService.findById(id);
        assertEquals(result, sensor);
    }

    @Test
    void findByIdWithEmptyIdTest()
    {
        // Arrange
        long id = 1L;
        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);

        when(mockSensorFactory.createSensorId(id)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> sensorService.findById(id)
        );
        String expectedMessage = "Bad id input";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findByIdWithNotFoundIdTest()
    {
        // Arrange
        long id = 1L;
        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);
        SensorId sensorId = mock(SensorId.class);

        when(mockSensorFactory.createSensorId(id)).thenReturn(Optional.of(sensorId));
        when(mockSensorRepository.findById(sensorId)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> sensorService.findById(id)
        );
        String expectedMessage = "Not found by id";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void findAllByDeviceId()
    {
        // Arrange
        long id = 1L;
        Sensor sensor = mock(Sensor.class);
        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);
        DeviceID deviceID = mock(DeviceID.class);

        when(mockDeviceFactory.createDeviceId(id)).thenReturn(Optional.of(deviceID));
        when(mockSensorRepository.findAllByDeviceId(id)).thenReturn(List.of(sensor));

        Iterable<Sensor> result = sensorService.findAllByDeviceId(id);
        Iterable<Sensor> expected = List.of(sensor);
        assertEquals(expected, result);
    }

    @Test
    void findAllByDeviceIdWithEmptyDeviceId()
    {
        // Arrange
        long id = 1L;
        SensorService sensorService = new SensorService(mockSensorRepository, mockSensorFactory, mockDeviceRepository, mockDeviceFactory);

        when(mockDeviceFactory.createDeviceId(id)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> sensorService.findAllByDeviceId(id)
        );
        String expectedMessage = "Bad id input";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

}
