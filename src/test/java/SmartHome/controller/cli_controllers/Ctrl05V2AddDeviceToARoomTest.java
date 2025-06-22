package SmartHome.controller.cli_controllers;

import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.DeviceDTO;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.service.DeviceService;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class Ctrl05V2AddDeviceToARoomTest
{

    private DeviceService deviceService;

    private Ctrl05v2AddDeviceToARoom ctrl05V2AddDeviceToARoom;

    @BeforeEach
    public void setup() {
        DeviceRepositoryImplMem deviceRepo = new DeviceRepositoryImplMem();
        DeviceFactory deviceFactory = new DeviceFactory();
        HouseId houseId = new HouseId(1L);
        Floor houseFloor = new Floor("RC");
        Dimensions dimensions = new Dimensions(3, 3, 3);
        RoomId roomid = new RoomId(1L);
        RoomName roomName = new RoomName("Bedroom");
        RoomFactory roomFactory = new RoomFactory();
        RoomRepositoryImplMem roomRepo = new RoomRepositoryImplMem();
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;
        Room bedroom = roomFactory.createRoom(roomName, roomid, houseId, houseFloor, dimensions, outdoorIndoor);
        roomRepo.save(bedroom);

        deviceService = new DeviceService(deviceFactory, deviceRepo, roomFactory, roomRepo);
        ctrl05V2AddDeviceToARoom = new Ctrl05v2AddDeviceToARoom(deviceService);
    }

    @Test
    void shouldAddDevice() {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";
        String deviceModel = "Xiaomi";
        DeviceDTO expectedDevice = new DeviceDTO(roomId, deviceId, deviceName, deviceModel);


        // Act
        Iterable<DeviceDTO> result = ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, deviceModel, roomId);

        // Assert
        assertEquals(expectedDevice, result.iterator().next());
        Pair<Boolean, DeviceID> addedDevice = deviceService.existsById(deviceId);
        assertTrue(addedDevice.getLeft());
    }

    @Test
    void shouldNotAddDuplicatedDevice() {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";
        String deviceModel = "Xiaomi";
        ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, deviceModel, roomId);

        // Act
        int initialSize = ((Collection<?>) ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, deviceModel, roomId)).size();
        ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, deviceModel, roomId);
        int finalSize = ((Collection<?>) ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, deviceModel, roomId)).size();

        // Assert
        assertEquals(initialSize, finalSize);
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", "\n", ""})
    void shouldThrowExceptionWhenDeviceModelIsInvalid(String model) {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";

        // Act and Assert
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, model, roomId),
                "Expected addDevice() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Device model cannot be null or empty"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", " \n"})
    @NullAndEmptySource
    void shouldNotAddDeviceModel(String model) {

        // Arrange
        long roomId = 1L;
        long deviceId = 1L;
        String deviceName = "Heater";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ctrl05V2AddDeviceToARoom.addDevice(deviceName, deviceId, model, roomId);
        });

        // Assert
        assertEquals("Device model cannot be null or empty", exception.getMessage());
    }
}
