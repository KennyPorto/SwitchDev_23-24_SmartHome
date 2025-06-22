package SmartHome.controller.cli_controllers;

import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.ActuatorDTO;
import SmartHome.persistence.mem.ActuatorRepositoryImplMem;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.service.ActuatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Ctrl12AddActuatorToDeviceInRoomTest
{
    private Ctrl12AddActuatorToDeviceInRoom ctrl12AddActuatorToDeviceInRoom;

    @BeforeEach
    void setup()
    {
        HouseId houseId = new HouseId(1L);
        Floor houseFloor = new Floor("RC");
        Dimensions dimensions = new Dimensions(3, 3, 3);
        RoomId roomId = new RoomId(1L);
        RoomName roomName = new RoomName("Bedroom");
        RoomFactory roomFactory = new RoomFactory();
        RoomRepositoryImplMem roomRepo = new RoomRepositoryImplMem();
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;
        Room bedroom = roomFactory.createRoom(roomName, roomId, houseId, houseFloor, dimensions, outdoorIndoor);
        roomRepo.save(bedroom);
        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        Device device = deviceFactory.createDevice(roomId, deviceId, deviceName, deviceModel, new DeviceStatus(true));

        DeviceRepository deviceRepository = new DeviceRepositoryImplMem();
        deviceRepository.save(device);
        ActuatorRepository actuatorRepository = new ActuatorRepositoryImplMem();
        ActuatorFactory actuatorFactory = new ActuatorFactory();
        ActuatorService actuatorService = new ActuatorService(actuatorRepository, actuatorFactory, deviceRepository, deviceFactory);
        ctrl12AddActuatorToDeviceInRoom = new Ctrl12AddActuatorToDeviceInRoom(actuatorService);

    }

    @Test
    void shouldAddActuator()
    {
        // Arrange
        String name = "SwitchOnOff";
        String model = "BlindRollerActuator";
        String actuatorType = "Binary";
        long actuatorId = 1L;
        long deviceId = 1L;
        ActuatorDTO actuatorDTO = new ActuatorDTO(name, model, actuatorId, deviceId, actuatorType);

        // Act
        boolean result = ctrl12AddActuatorToDeviceInRoom.addActuator(actuatorDTO);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldNotAddDuplicatedActuator()
    {

        // Arrange
        String name = "SwitchOnOff";
        String model = "SwitchOnOffActuator";
        String actuatorType = "Binary";
        long actuatorId = 1L;
        long deviceId = 1L;
        ActuatorDTO actuatorDTO = new ActuatorDTO(name, model, actuatorId, deviceId, actuatorType);
        ctrl12AddActuatorToDeviceInRoom.addActuator(actuatorDTO);

        // Act
        boolean result = ctrl12AddActuatorToDeviceInRoom.addActuator(actuatorDTO);

        // Assert
        assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", " \n"})
    @NullAndEmptySource
    void shouldNotAddActuatorName(String name)
    {
        // Arrange
        String model = "SwitchOnOffActuator";
        String actuatorType = "Binary";
        long actuatorId = 1L;
        long deviceId = 1L;
        ActuatorDTO actuatorDTO = new ActuatorDTO(name, model, actuatorId, deviceId, actuatorType);

        // Act and Assert
        assertFalse(ctrl12AddActuatorToDeviceInRoom.addActuator(actuatorDTO));
    }

}
