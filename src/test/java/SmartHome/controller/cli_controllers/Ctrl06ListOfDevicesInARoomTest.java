package SmartHome.controller.cli_controllers;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.DeviceDTO;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.HouseRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.service.DeviceService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Ctrl06ListOfDevicesInARoomTest
{

    @Test
    void getListDevicesByRoomId_success() {
        // Arrange
        RoomRepositoryImplMem roomRepository = new RoomRepositoryImplMem();
        RoomFactory roomFactory = new RoomFactory();
        HouseFactory houseFactory = new HouseFactory();
        HouseRepository houseRepository = new HouseRepositoryImplMem();

        Address address = new Address("Rua", "123", "1234-123", "Porto", "Portugal");
        GPS gps = new GPS(1.0, 1.0);
        HouseId houseId = new HouseId(1L);
        House house = houseFactory.createHouse(houseId, address, gps);
        houseRepository.save(house);

        RoomName roomName = new RoomName("Living Room");
        RoomId roomId = new RoomId(1);
        Floor houseFloor = new Floor("floor1");
        Dimensions dimensions = new Dimensions(2.0, 3.0, 4.0);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;
        Room room = roomFactory.createRoom(roomName, roomId, houseId, houseFloor, dimensions, outdoorIndoor);
        roomRepository.save(room);

        DeviceID deviceId1 = new DeviceID(1);
        DeviceName deviceName1 = new DeviceName("DeviceName1");
        DeviceModel deviceModel = new DeviceModel("DeviceModel");
        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceRepositoryImplMem deviceRepository = new DeviceRepositoryImplMem();

        Device device1 = deviceFactory.createDevice(roomId, deviceId1, deviceName1, deviceModel,
                new DeviceStatus(true));
        DeviceDTO deviceDTO1 = new DeviceDTO(1L, 1, "DeviceName1", "DeviceModel");

        deviceRepository.save(device1);

        DeviceService service = new DeviceService(deviceFactory, deviceRepository, roomFactory, roomRepository);
        Ctrl06ListOfDevicesInARoom ctrl06ListOfDevicesInARoom = new Ctrl06ListOfDevicesInARoom(service);

        Iterable<DeviceDTO> result = ctrl06ListOfDevicesInARoom.getListDevicesByRoom(1L);
        Iterable<DeviceDTO> expected = List.of(deviceDTO1);

        // Assert
        assertEquals(expected.iterator().next().deviceName, result.iterator().next().deviceName);
        assertEquals(1, ((List<DeviceDTO>) result).size());
    }
    @Test
    void getAllRooms_EmptyList() {
        // Arrange
        RoomRepositoryImplMem roomRepository = new RoomRepositoryImplMem();
        RoomFactory roomFactory = new RoomFactory();
        DeviceFactory deviceFactory = new DeviceFactory();
        DeviceRepositoryImplMem deviceRepository = new DeviceRepositoryImplMem();
        DeviceService service = new DeviceService(deviceFactory, deviceRepository, roomFactory, roomRepository);
        Ctrl06ListOfDevicesInARoom ctrl06ListOfDevicesInARoom = new Ctrl06ListOfDevicesInARoom(service);

        // Act
        Iterable<DeviceDTO> result = ctrl06ListOfDevicesInARoom.getListDevicesByRoom(1L);
        Iterable<DeviceDTO> expected = List.of();

        // Assert
        assertEquals(expected, result);
    }
}
