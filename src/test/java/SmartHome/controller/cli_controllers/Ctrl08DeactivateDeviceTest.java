package SmartHome.controller.cli_controllers;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Ctrl08DeactivateDeviceTest
{

    Ctrl08DeactivateDevice ctrl08DeactivateDevice;

    @BeforeEach
    public void setup() {
        HouseId houseId = new HouseId(1L);

        RoomRepositoryImplMem roomRepository = new RoomRepositoryImplMem();
        RoomFactory roomFactory = new RoomFactory();
        RoomName roomName = new RoomName("Living Room");
        RoomId roomId = new RoomId(1);
        Floor houseFloor = new Floor("floor1");
        Dimensions dimensions = new Dimensions(2.0, 3.0, 4.0);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;
        Room room = roomFactory.createRoom(roomName, roomId, houseId, houseFloor, dimensions, outdoorIndoor);
        roomRepository.save(room);

        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceRepositoryImplMem deviceRepository = new DeviceRepositoryImplMem();
        DeviceService deviceService = new DeviceService(deviceFactory, deviceRepository, roomFactory, roomRepository);
        DeviceID deviceId = new DeviceID(1);
        DeviceName deviceName = new DeviceName("DeviceName");
        DeviceModel deviceModel = new DeviceModel("DeviceModel");
        Device device = deviceFactory
                .createDevice(roomId, deviceId, deviceName, deviceModel, new DeviceStatus(true));
        deviceRepository.save(device);

        ctrl08DeactivateDevice = new Ctrl08DeactivateDevice(deviceService);
    }

    @Test
    void validDeactivateDevice() {
        //Arrange
        long deviceId = 1L;

        //Act
        Device result = ctrl08DeactivateDevice.deactivateDevice(deviceId);

        //Assert
        assertFalse(result.getDeviceStatus().activated);
    }

    @Test
    void invalidDeactivateNotExistingDevice() {
        //Arrange
        long deviceId = 2L;

        // Act and Assert
        try {
            ctrl08DeactivateDevice.deactivateDevice(deviceId);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Device not found", e.getMessage());
        }
    }

    @Test
    void invalidDeactivateInvalidId()
    {
        //Arrange
        long deviceId = -1L;

        // Act and Assert
        try {
            ctrl08DeactivateDevice.deactivateDevice(deviceId);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid device ID", e.getMessage());
        }
    }

    @Test
    void invalidDeactivateAlreadyDeactivated()
    {
        //Arrange
        long deviceId = 1L;
        ctrl08DeactivateDevice.deactivateDevice(deviceId);

        // Act and Assert
        try {
            ctrl08DeactivateDevice.deactivateDevice(deviceId);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Device is not activated", e.getMessage());
        }
    }

}
