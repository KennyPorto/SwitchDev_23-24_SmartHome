package SmartHome.controller.cli_controllers;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.RoomDTO;
import SmartHome.persistence.mem.HouseRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Ctrl03ListOfAllRoomsTest
{

    private Ctrl03ListOfAllRooms ctrl03;
    private RoomFactory roomFactory;
    private RoomRepositoryImplMem roomRepository;
    private HouseFactory houseFactory;
    private HouseRepository houseRepository;

    @BeforeEach
    void setUp() {
        roomRepository = new RoomRepositoryImplMem();
        roomFactory = new RoomFactory();
        houseFactory = new HouseFactory();
        houseRepository = new HouseRepositoryImplMem();
        RoomService roomService = new RoomService(roomRepository, roomFactory, houseFactory, houseRepository);
        ctrl03 = new Ctrl03ListOfAllRooms(roomService);
    }


    @Test
    void getAllRooms_notEmptyList() {
        // Arrange
        Address address = new Address("Rua", "123", "1234-123", "Porto", "Portugal");
        GPS gps = new GPS(1.0, 1.0);
        HouseId houseId = new HouseId(1L);
        House house = houseFactory.createHouse(houseId, address, gps);
        houseRepository.save(house);

        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 1L, "floor1", 2.0, 3.0, 4.0, "OUTDOOR");

        RoomName roomName = new RoomName("Living Room");
        RoomId roomId = new RoomId(1);
        Floor houseFloor = new Floor("floor1");
        Dimensions dimensions = new Dimensions(2.0, 3.0, 4.0);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;
        Room room = roomFactory.createRoom(roomName, roomId, houseId, houseFloor, dimensions, outdoorIndoor);
        roomRepository.save(room);

        // Act
        Iterable<RoomDTO> result = ctrl03.getAllRooms(1L);
        Iterable<RoomDTO> expected = List.of(roomDTO);

        // Assert
        assertEquals(expected.iterator().next().roomName, result.iterator().next().roomName);
    }

    @Test
    void getAllRooms_EmptyList() {
        // Act
        Iterable<RoomDTO> result = ctrl03.getAllRooms(1L);
        Iterable<RoomDTO> expected = List.of();

        // Assert
        assertEquals(expected, result);
    }

}
