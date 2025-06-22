package SmartHome.controller.cli_controllers;

import SmartHome.domain.house.House;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.Address;
import SmartHome.domain.valueObjects.GPS;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.mapper.RoomDTO;
import SmartHome.persistence.mem.HouseRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Ctrl02AddRoomToHouseTest
{
    private HouseFactory houseFactory;
    private HouseRepository houseRepository;
    private Ctrl02AddRoomToHouse ctrl02AddRoomToHouse;

    @BeforeEach
    public void setup() {
        RoomRepositoryImplMem roomRepository = new RoomRepositoryImplMem();
        RoomFactory roomFactory = new RoomFactory();
        houseFactory = new HouseFactory();
        houseRepository = new HouseRepositoryImplMem();
        RoomService roomService = new RoomService(roomRepository, roomFactory, houseFactory, houseRepository);
        ctrl02AddRoomToHouse = new Ctrl02AddRoomToHouse(roomService);
    }

    @Test
    void testAddRoomToHouse_Success() {
        // Arrange
        Address address = new Address("Rua", "123", "1234-123",
                "Porto", "Portugal");
        GPS gps = new GPS(1.0, 1.0);
        HouseId houseId = new HouseId(1L);
        House house = houseFactory.createHouse(houseId, address, gps);
        houseRepository.save(house);
        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 1L, "floor1",
                2.0, 3.0, 4.0, "OUTDOOR");

        // Act
        boolean result = ctrl02AddRoomToHouse.addRoomToHouse(roomDTO);

        // Assert
        assertTrue(result);
    }

    @Test
    void testAddRoomToHouse_HouseNotFound() {
        // Arrange
        RoomDTO roomDTO = new RoomDTO("Living Room", 1, 1L, "floor1",
                2.0, 3.0, 4.0, "OUTDOOR");

        // Act
        boolean result = ctrl02AddRoomToHouse.addRoomToHouse(roomDTO);

        // Assert
        assertFalse(result);
    }

    @Test
    void testAddRoomToHouse_FailureEmptyRoomName() {
        // Arrange
        Address address = new Address("Rua", "123", "1234-123",
                "Porto", "Portugal");
        GPS gps = new GPS(1.0, 1.0);
        HouseId houseId = new HouseId(1L);
        House house = houseFactory.createHouse(houseId, address, gps);
        houseRepository.save(house);
        RoomDTO roomDTO = new RoomDTO(" ", 1, 1L, "floor1", 2.0,
                3.0, 4.0, "OUTDOOR");

        // Act
        boolean result = ctrl02AddRoomToHouse.addRoomToHouse(roomDTO);

        // Assert
        assertFalse(result);
    }

    @Test
    void testAddRoomToHouse_FailureNullDto() {
        // Arrange
        Address address = new Address("Rua", "123", "1234-123",
                "Porto", "Portugal");
        GPS gps = new GPS(1.0, 1.0);
        HouseId houseId = new HouseId(1L);
        House house = houseFactory.createHouse(houseId, address, gps);
        houseRepository.save(house);

        // Act
        boolean result = ctrl02AddRoomToHouse.addRoomToHouse(null);

        // Assert
        assertFalse(result);
    }
}