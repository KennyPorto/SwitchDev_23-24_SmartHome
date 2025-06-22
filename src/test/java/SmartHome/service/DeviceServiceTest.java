package SmartHome.service;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.DeviceDTO;
import SmartHome.mapper.DeviceMapper;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceServiceTest {

    @Test
    void validDeviceService() {

        // Arrange
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        // Act + Assert
        new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
    }

    @Test
    void invalidDeviceServiceDeviceFactoryNull() {

        // Arrange
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceService(null, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        });
        String result = exception.getMessage();

        // Assert
        assertNull(result);
    }

    @Test
    void invalidDeviceServiceDeviceRepositoryNull() {

        // Arrange
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceService(deviceFactoryDouble, null, roomFactoryDouble, roomRepositoryDouble);
        });
        String result = exception.getMessage();

        // Assert
        assertNull(result);
    }

    @Test
    void invalidDeviceServiceRoomFactoryNull() {

        // Arrange
        DeviceFactory deviceFactory = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceService(deviceFactory, deviceRepoDouble, null, roomRepositoryDouble);
        });
        String result = exception.getMessage();

        // Assert
        assertNull(result);
    }

    @Test
    void invalidDeviceServiceRoomRepositoryNull() {

        // Arrange
        DeviceFactory deviceFactory = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceService(deviceFactory, deviceRepoDouble, roomFactoryDouble, null);
        });
        String result = exception.getMessage();

        // Assert
        assertNull(result);
    }

    @Test
    void shouldAddDevice() {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";
        String deviceModel = "Xiaomi";
        DeviceDTO deviceDTO = new DeviceDTO(roomId, deviceId, deviceName, deviceModel);
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        Device deviceDouble = mock(Device.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.of((deviceIdDouble)));
        when(deviceFactoryDouble.createDeviceName(deviceName)).thenReturn(Optional.of(deviceNameDouble));
        when(deviceFactoryDouble.createDeviceModel(deviceModel)).thenReturn(Optional.of(deviceModelDouble));
        when(deviceFactoryDouble.createDevice(roomIdDouble, deviceIdDouble, deviceNameDouble, deviceModelDouble,
                new DeviceStatus(true))).thenReturn(deviceDouble);
        when(deviceDouble.getRoomId()).thenReturn(roomIdDouble);
        when(deviceDouble.identity()).thenReturn(deviceIdDouble);
        when(deviceDouble.getName()).thenReturn(deviceNameDouble);
        when(deviceDouble.getModel()).thenReturn(deviceModelDouble);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.save(deviceDouble)).thenReturn(deviceDouble); // Ensure the save method returns the device
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        when(roomFactoryDouble.createRoomId(roomId)).thenReturn(Optional.of(roomIdDouble));
        when(roomRepositoryDouble.existsById(roomIdDouble)).thenReturn(true);

        // Mocking static method
        try (MockedStatic<DeviceMapper> devMapper = Mockito.mockStatic(DeviceMapper.class)) {
            devMapper.when(() -> DeviceMapper.deviceToDto(deviceDouble))
                    .thenReturn(deviceDTO);

            // Act
            Device result = deviceService.add(deviceNameDouble, deviceIdDouble, deviceModelDouble, roomIdDouble);

            // Assert
            assertNotNull(result, "Expected a non-null device, but got null");
        }
    }
    @Test
    void shouldNotAddDeviceWithNonExistingRoom() {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";
        String deviceModel = "Xiaomi";
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        Device deviceDouble = mock(Device.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.of((deviceIdDouble)));
        when(deviceFactoryDouble.createDeviceName(deviceName)).thenReturn(Optional.of(deviceNameDouble));
        when(deviceFactoryDouble.createDeviceModel(deviceModel)).thenReturn(Optional.of(deviceModelDouble));
        when(deviceFactoryDouble.createDevice(roomIdDouble, deviceIdDouble, deviceNameDouble,
                deviceModelDouble, new DeviceStatus(true))).thenReturn(deviceDouble);
        when(deviceDouble.getRoomId()).thenReturn(roomIdDouble);
        when(deviceDouble.identity()).thenReturn(deviceIdDouble);
        when(deviceDouble.getName()).thenReturn(deviceNameDouble);
        when(deviceDouble.getModel()).thenReturn(deviceModelDouble);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        when(roomFactoryDouble.createRoomId(roomId)).thenReturn(Optional.of(roomIdDouble));
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // call the method that is expected to throw the exception
            deviceService.add(deviceNameDouble, deviceIdDouble, deviceModelDouble, roomIdDouble);
        });

        String expectedMessage = "Room id doesn't exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotAddDeviceWithDuplicatedDeviceId() {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";
        String deviceModel = "Xiaomi";
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        Device deviceDouble = mock(Device.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.of((deviceIdDouble)));
        when(deviceFactoryDouble.createDeviceName(deviceName)).thenReturn(Optional.of(deviceNameDouble));
        when(deviceFactoryDouble.createDeviceModel(deviceModel)).thenReturn(Optional.of(deviceModelDouble));
        when(deviceFactoryDouble.createDevice(roomIdDouble, deviceIdDouble, deviceNameDouble,
                deviceModelDouble, new DeviceStatus(true))).thenReturn(deviceDouble);
        when(deviceDouble.getRoomId()).thenReturn(roomIdDouble);
        when(deviceDouble.identity()).thenReturn(deviceIdDouble);
        when(deviceDouble.getName()).thenReturn(deviceNameDouble);
        when(deviceDouble.getModel()).thenReturn(deviceModelDouble);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.save(deviceDouble)).thenReturn(deviceDouble);
        when(deviceRepoDouble.existsById(deviceIdDouble)).thenReturn(true);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        when(roomFactoryDouble.createRoomId(roomId)).thenReturn(Optional.of(roomIdDouble));
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        when(roomRepositoryDouble.existsById(roomIdDouble)).thenReturn(true);

        // Act
        deviceService.add(deviceNameDouble, deviceIdDouble, deviceModelDouble, roomIdDouble);
        int initialSize = ((Collection<?>) deviceService.findAll()).size();
        deviceService.add(deviceNameDouble, deviceIdDouble, deviceModelDouble, roomIdDouble);
        int finalSize = ((Collection<?>) deviceService.findAll()).size();

        // Assert
        assertEquals(initialSize, finalSize);
    }

    @Test
    void shouldAddDeviceWithNullName() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        when(roomRepositoryDouble.existsById(roomIdDouble)).thenReturn(true); // Add this line
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble,
                roomRepositoryDouble);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            deviceService.add(null, deviceIdDouble, deviceModelDouble, roomIdDouble);
        });

        String expectedMessage = "Invalid device attributes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionWhenAddingDeviceWithNullName() {

        // Arrange
        RoomId roomIdDouble = mock(RoomId.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        when(roomRepositoryDouble.existsById(roomIdDouble)).thenReturn(true);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act and Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> deviceService.add(null, deviceIdDouble, deviceModelDouble, roomIdDouble),
                "Expected add() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Invalid device attributes"));
    }

    @Test
    void shouldGetDevices() {
        // Arrange
        Device myDevice1Double = mock(Device.class);
        Device myDevice2Double = mock(Device.class);
        List<Device> expectedDevices = List.of(myDevice1Double, myDevice2Double);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.findAll()).thenReturn(expectedDevices);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        Device deviceDTO1 = mock(Device.class);
        Device deviceDTO2 = mock(Device.class);

        try (MockedStatic<DeviceMapper> devMapper = Mockito.mockStatic(DeviceMapper.class)) {
            devMapper.when(() -> DeviceMapper.deviceListToDto(expectedDevices))
                    .thenReturn(List.of(deviceDTO1, deviceDTO2));

            // Act
            Iterable<Device> devices = deviceService.findAll();

            // Assert
            assertNotNull(devices);
            assertEquals(expectedDevices.size(), ((Collection<?>) devices).size());
        }
    }

    @Test
    void shouldCheckDeviceExistsById() {

        // Arrange
        long deviceId = 1L;
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.of(deviceIdDouble));
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.existsById(deviceIdDouble)).thenReturn(true);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act
        Pair<Boolean, DeviceID> result = deviceService.existsById(deviceId);

        // Assert
        assertTrue(result.getLeft());
        assertEquals(deviceIdDouble, result.getRight());
    }

    @Test
    void shouldCheckDeviceExistsById_deviceIdDoesntExist() {

        // Arrange
        long deviceId = 1L;
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.of(deviceIdDouble));
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.existsById(deviceIdDouble)).thenReturn(false);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act
        Pair<Boolean, DeviceID> result = deviceService.existsById(deviceId);

        // Assert
        assertFalse(result.getLeft());
        assertEquals(deviceIdDouble, result.getRight());
    }

    @Test
    void shouldCheckDeviceExistsById_invalidIdParameter() {

        // Arrange
        long deviceId = -1L;
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.empty());
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.existsById(deviceIdDouble)).thenReturn(true);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act
        Pair<Boolean, DeviceID> result = deviceService.existsById(deviceId);

        // Assert
        assertFalse(result.getLeft());
        assertNotEquals(deviceIdDouble, result.getRight());
    }

    @Test
    void shouldFindAllByRoomId() {
        // Arrange
        long roomId = 1L;
        RoomId roomIdDouble = mock(RoomId.class);
        Device myDevice1Double = mock(Device.class);
        when(myDevice1Double.getRoomId()).thenReturn(roomIdDouble);
        Device myDevice2Double = mock(Device.class);
        when(myDevice2Double.getRoomId()).thenReturn(roomIdDouble);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.findAllByRoomId(roomId)).thenReturn(List.of(myDevice1Double, myDevice2Double));
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        when(roomFactoryDouble.createRoomId(roomId)).thenReturn(Optional.of(roomIdDouble));
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);

        try (MockedStatic<DeviceMapper> devMapper = Mockito.mockStatic(DeviceMapper.class)) {
            devMapper.when(() -> DeviceMapper.deviceListToDto(deviceRepoDouble.findAllByRoomId(roomId)))
                    .thenReturn(List.of(device1, device2));

            // Act
            Iterable<Device> devices = deviceService.findAllByRoomId(roomId);
            Iterable<DeviceDTO> expected = List.of();

            // Assert
            assertEquals(expected, devices);
        }
    }

    @Test
    void shouldReturnEmptyIterableForNotValidRoomId() {
        // Arrange
        long roomId = -1L;
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        RoomId roomIdDouble = mock(RoomId.class);
        when(roomFactoryDouble.createRoomId(roomId)).thenReturn(Optional.of(roomIdDouble));

        // Act
        Iterable<Device> result = deviceService.findAllByRoomId(roomId);
        Iterable<Device> expected = List.of();

        // Assert
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void deactivateById() {
        // Arrange
        long devId = 1L;
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        RoomId roomId = mock(RoomId.class);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceID deviceID1 = new DeviceID(devId);
        Device device = deviceFactory.createDevice(roomId, deviceID1, deviceName, deviceModel, new DeviceStatus(true));
        when(deviceRepoDouble.findById(deviceID1)).thenReturn(Optional.of(device));
        when(deviceRepoDouble.save(any())).thenReturn(device);

        DeviceService deviceService = new DeviceService(deviceFactory, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act
        Device result = deviceService.deactivateDevice(devId);

        // Assert
        assertFalse(result.getDeviceStatus().activated);
    }

    @Test
    void isActiveByIdAlreadyDeactivated() {
        // Arrange
        long devId = 1L;
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        RoomId roomId = mock(RoomId.class);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceID deviceID1 = new DeviceID(devId);
        Device device = deviceFactory.createDevice(roomId, deviceID1, deviceName, deviceModel, new DeviceStatus(true));
        when(deviceRepoDouble.findById(deviceID1)).thenReturn(Optional.of(device));

        DeviceService deviceService = new DeviceService(deviceFactory, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);
        deviceService.deactivateDevice(devId);

        // Act and Assert
        try {
            deviceService.deactivateDevice(devId);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Device is not activated", e.getMessage());
        }
    }

    @Test
    void isActiveById_invalidId() {
        // Arrange
        long devId = -1L;
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        when(deviceFactoryDouble.createDeviceId(devId)).thenReturn(Optional.empty());

        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act and Assert
        try {
            deviceService.deactivateDevice(devId);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid device ID", e.getMessage());
        }
    }

    @Test
    void isActiveById_DeviceNotFound()
    {
        // Arrange
        long devId = 1L;
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);

        DeviceID deviceIdDouble = mock(DeviceID.class);
        Device deviceDouble = mock(Device.class);

        when(deviceFactoryDouble.createDeviceId(devId)).thenReturn(Optional.of(deviceIdDouble));
        when(deviceRepoDouble.findById(deviceIdDouble)).thenReturn(Optional.empty());
        when(deviceDouble.deactivateDevice()).thenReturn(false);

        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act and Assert
        try {
            deviceService.deactivateDevice(devId);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Device not found", e.getMessage());
        }
    }

    @Test
    void shouldCheckFindDeviceById() {

        // Arrange
        long deviceId = 1L;
        Device deviceDouble = mock(Device.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        when(deviceFactoryDouble.createDeviceId(deviceId)).thenReturn(Optional.of(deviceIdDouble));
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        when(deviceRepoDouble.findById(deviceIdDouble)).thenReturn(Optional.of(deviceDouble));
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        DeviceDTO deviceDTODouble = new DeviceDTO(1L, 1L, "DeviceName", "DeviceModel");

        try (MockedStatic<DeviceMapper> mapper = Mockito.mockStatic(DeviceMapper.class)) {
            mapper.when(() -> DeviceMapper.deviceToDto(deviceDouble))
                    .thenReturn(deviceDTODouble); // Return the DeviceDTO object

            // Act
            Device result = deviceService.findById(deviceId);

            // Assert
            assertNotNull(result);
        }
    }

    @Test
    void shouldNotCheckFindDeviceById() {

        // Arrange
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        DeviceID deviceIdDouble = mock(DeviceID.class);
        when(deviceRepoDouble.findById(deviceIdDouble))
                .thenReturn(null);
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act
        Device result = deviceService.findById(deviceIdDouble.id);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldReturnEmptyListWhenRoomIdIsNotPresent() {

        // Arrange
        long roomId = -1L;
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepositoryImplMem deviceRepoDouble = mock(DeviceRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepositoryImplMem roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        when(roomFactoryDouble.createRoomId(roomId)).thenReturn(Optional.empty());
        DeviceService deviceService = new DeviceService(deviceFactoryDouble, deviceRepoDouble, roomFactoryDouble, roomRepositoryDouble);

        // Act
        Iterable<Device> result = deviceService.findAllByRoomId(roomId);

        // Assert
        assertFalse(result.iterator().hasNext());
    }

}