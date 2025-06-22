package SmartHome.persistence.mem;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceRepositoryImplMemTest
{
    DeviceID deviceIDDouble;
    Device myDeviceDouble;
    DeviceRepositoryImplMem repo;

    @BeforeEach
    public void setup() {
        deviceIDDouble = mock(DeviceID.class);
        myDeviceDouble = mock(Device.class);
        repo = new DeviceRepositoryImplMem();
    }

    @Test
    void shouldSaveDevice() {
        // Arrange
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);

        // Act
        Device result = repo.save(myDeviceDouble);
        Device expected = myDeviceDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void shouldSaveDeviceAndConfirmsItExists() {

        // Arrange
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);
        repo.save(myDeviceDouble);

        // Act
        boolean result = repo.existsById(deviceIDDouble);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldReturnNotExistsById() {

        // Arrange
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);
        repo.save(myDeviceDouble);
        DeviceID deviceIdDouble2 = mock(DeviceID.class);

        // Act
        boolean result = repo.existsById(deviceIdDouble2);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldFindById() {

        // Arrange
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);
        repo.save(myDeviceDouble);

        // Act
        Optional<Device> result = repo.findById(deviceIDDouble);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(myDeviceDouble, result.get());
    }

    @Test
    void shouldFindInactiveDeviceById() {
        // Arrange
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);
        when(myDeviceDouble.getDeviceStatus()).thenReturn(deviceStatus);
        repo.save(myDeviceDouble);

        // Act
        boolean result = repo.isActiveById(deviceIDDouble);

        // Assert
        assertFalse(result);
    }

    @Test
    void shouldFindActiveDeviceById() {
        // Arrange
        DeviceStatus deviceStatus = new DeviceStatus(true);
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);
        when(myDeviceDouble.getDeviceStatus()).thenReturn(deviceStatus);
        repo.save(myDeviceDouble);

        // Act
        boolean result = repo.isActiveById(deviceIDDouble);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldFindByIdEmptyOptional() {

        // Arrange
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);

        // Act
        Optional<Device> result = repo.findById(deviceIDDouble);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void shouldFindAllDevices() {

        // Arrange
        DeviceID deviceIDDouble1 = mock(DeviceID.class);
        Device myDeviceDouble1 = mock(Device.class);
        when(myDeviceDouble.identity()).thenReturn(deviceIDDouble);
        when(myDeviceDouble1.identity()).thenReturn(deviceIDDouble1);
        repo.save(myDeviceDouble);
        repo.save(myDeviceDouble1);

        // Act
        Iterable<Device> result = repo.findAll();

        // Assert
        List<Device> devices = StreamSupport.stream(result.spliterator(), false).toList();
        assertTrue(devices.contains(myDeviceDouble));
        assertTrue(devices.contains(myDeviceDouble1));
    }

    @Test
    void shouldFindAllDevicesFromGivenRoomId() {
        // Arrange
        long rId = 1L;
        RoomId roomIdDouble1 = mock(RoomId.class);
        RoomId roomIdDouble2 = mock(RoomId.class);
        Device deviceDouble1 = mock(Device.class);
        Device deviceDouble2 = mock(Device.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        Device device = new DeviceFactory()
                .createDevice(new RoomId(rId), deviceIDDouble, deviceNameDouble, deviceModelDouble, new DeviceStatus(true));
        DeviceID deviceID1 = mock(DeviceID.class);
        DeviceID deviceID2 = mock(DeviceID.class);
        when(deviceDouble1.getRoomId()).thenReturn(roomIdDouble1);
        when(deviceDouble2.getRoomId()).thenReturn(roomIdDouble2);
        when(deviceDouble1.identity()).thenReturn(deviceID1);
        when(deviceDouble2.identity()).thenReturn(deviceID2);

        DeviceRepositoryImplMem deviceRepository = new DeviceRepositoryImplMem();
        deviceRepository.save(device);
        deviceRepository.save(deviceDouble2);

        // Act
        Iterable<Device> result = deviceRepository.findAllByRoomId(rId);
        Iterable<Device> expected = List.of(device);

        // Assert
        assertEquals(expected.toString(), result.toString());
    }
}