package SmartHome.controller.cli_controllers;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.repository.SensorTypeRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.SensorDTO;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.persistence.mem.SensorRepositoryImplMem;
import SmartHome.persistence.mem.SensorTypeRepositoryImplMem;
import SmartHome.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Ctrl07AddSensorToDeviceInRoomTest
{
    private Ctrl07AddSensorToDeviceInRoom ctrl07AddSensorToDeviceInRoom;
    SensorTypeRepository sensorTypeRepository;
    private DeviceRepository deviceRepository;

    @BeforeEach
    public void setup() {
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
        DeviceRepositoryImplMem deviceRepo = new DeviceRepositoryImplMem();
        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceID deviceId = new DeviceID(1L);
        DeviceName deviceName = new DeviceName("Heater");
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        Device device = deviceFactory.createDevice(roomId, deviceId, deviceName, deviceModel, new DeviceStatus(true));
        deviceRepo.save(device);

        SensorRepository sensorRepository = new SensorRepositoryImplMem();
        SensorFactory sensorFactory = new SensorFactory();
        deviceRepository = new DeviceRepositoryImplMem();
        sensorTypeRepository = new SensorTypeRepositoryImplMem();
        SensorService sensorService = new SensorService(sensorRepository, sensorFactory, deviceRepository, deviceFactory);
        ctrl07AddSensorToDeviceInRoom = new Ctrl07AddSensorToDeviceInRoom(sensorService);
    }

    @Test
    void shouldAddSensor()
    {
        // Arrange
        String name = "BinarySwitch";
        String model = "BinarySwitch";
        SensorType sensorType = new SensorTypeFactory().createSensorType(new SensorTypeId("BinarySwitch"), MeasurementUnit.Binary);
        String sensorTypeId = sensorType.identity().id;
        long sensorId = 1L;
        long deviceId = 1L;
        sensorTypeRepository.save(sensorType);
        SensorDTO sensorDTO = new SensorDTO(name, model, sensorId, deviceId, sensorTypeId);
        DeviceFactory deviceFactory = mock(DeviceFactory.class);
        when(deviceFactory.createDeviceStatus(true)).thenReturn(new DeviceStatus(true));
        DeviceName deviceName = new DeviceName("devName");
        DeviceModel deviceModel = new DeviceModel("DevModel");
        DeviceID deviceID = new DeviceID(1L);
        DeviceStatus deviceStatus = new DeviceStatus(true);
        RoomId roomId = new RoomId(2);

        Device device = mock(Device.class);
        when(deviceFactory.createDevice(roomId, deviceID, deviceName, deviceModel, new DeviceStatus(true))).thenReturn(device);
        when(deviceFactory.createDeviceStatus(true)).thenReturn(deviceStatus);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);
        deviceRepository.save(device);
        // Act
        boolean result = ctrl07AddSensorToDeviceInRoom.addSensor(sensorDTO);

        // Assert
        assertTrue(result);
    }



    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", " \n"})
    @NullAndEmptySource
    void shouldNotAddSensorName(String name) {

        // Arrange
        String model = "BinarySwitch";
        String sensorType = "BinarySwitch";
        long sensorId = 1L;
        long deviceId = 1L;
        SensorDTO sensorDTO = new SensorDTO(name, model, sensorId, deviceId, sensorType);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ctrl07AddSensorToDeviceInRoom.addSensor(sensorDTO);
        });
    }
    @Test
    void shouldNotAddSensorWithInvalidTypeId() {

        // Arrange
        String name = "BinarySwitch";
        String model = "InvalidModel";
        String invalidSensorTypeId = "InvalidTypeId";
        long sensorId = 1L;
        long deviceId = 1L;
        SensorDTO sensorDTO = new SensorDTO(name, model, sensorId, deviceId, invalidSensorTypeId);
        DeviceFactory deviceFactory = mock(DeviceFactory.class);
        when(deviceFactory.createDeviceStatus(true)).thenReturn(new DeviceStatus(true));
        DeviceName deviceName = new DeviceName("devName");
        DeviceModel deviceModel = new DeviceModel("DevModel");
        DeviceID deviceID = new DeviceID(1L);
        DeviceStatus deviceStatus = new DeviceStatus(true);
        RoomId roomId = new RoomId(2);
        Device device = mock(Device.class);
        when(deviceFactory.createDevice(roomId, deviceID, deviceName, deviceModel, new DeviceStatus(true))).thenReturn(device);
        when(deviceFactory.createDeviceStatus(true)).thenReturn(deviceStatus);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);
        deviceRepository.save(device);

        // Act
        boolean result = ctrl07AddSensorToDeviceInRoom.addSensor(sensorDTO);

        // Assert
        assertFalse(result);
    }
}
