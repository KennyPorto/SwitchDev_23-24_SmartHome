package SmartHome.domain.room;

import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class RoomFactoryTest {

    @Test
    void createValidRoomWithIsolation() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        RoomName roomNameDouble = mock(RoomName.class);
        RoomId roomIdDouble = mock(RoomId.class);
        HouseId houseIdDouble = mock(HouseId.class);
        Floor floorDouble = mock(Floor.class);
        Dimensions dimensionsDouble = mock(Dimensions.class);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;

        // Act
        Room roomDouble = roomFactory.createRoom(roomNameDouble, roomIdDouble, houseIdDouble, floorDouble, dimensionsDouble, outdoorIndoor);

        // Assert
        assertEquals(roomNameDouble, roomDouble.getRoomName());
        assertEquals(roomIdDouble, roomDouble.identity());
        assertEquals(houseIdDouble, roomDouble.getHouseId());
        assertEquals(floorDouble, roomDouble.getHouseFloor());
        assertEquals(dimensionsDouble, roomDouble.getDimensions());
    }


    @Test
    void createValidRoom() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2,3,4);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;

        // Act
        Room room = roomFactory.createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Assert
        assertEquals(roomName, room.getRoomName());
        assertEquals(roomId, room.identity());
        assertEquals(houseId, room.getHouseId());
        assertEquals(floor, room.getHouseFloor());
        assertEquals(dimensions, room.getDimensions());
    }

    @Test
    void createValidRoomIndoor()
    {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        RoomName roomName = new RoomName("RoomName");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("Floor1");
        Dimensions dimensions = new Dimensions(2, 3, 4);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.INDOOR;

        // Act
        Room room = roomFactory.createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Assert
        assertEquals(outdoorIndoor, room.getOutdoorIndoor());
    }

    @Test
    void createValidRoomName() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        String name = "Living Room";

        // Act
        Optional<RoomName> roomName = roomFactory.createRoomName(name);

        // Assert
        assertTrue(roomName.isPresent());
    }

    @Test
    void createRoomName_fail() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        String name = "";

        // Act
        Optional<RoomName> roomName = roomFactory.createRoomName(name);

        // Assert
        assertTrue(roomName.isEmpty());
    }

    @Test
    void createValidRoomId() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        long id = 1;

        // Act
        Optional<RoomId> roomId = roomFactory.createRoomId(id);

        // Assert
        assertTrue(roomId.isPresent());
    }

    @Test
    void createValidRoomId_fail() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        long id = -5;

        // Act
        Optional<RoomId> roomId = roomFactory.createRoomId(id);

        // Assert
        assertTrue(roomId.isEmpty());
    }

    @Test
    void createValidFloor() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        String floor = "floor";

        // Act
        Optional<Floor> houseFloor = roomFactory.createFloor(floor);

        // Assert
        assertTrue(houseFloor.isPresent());
    }

    @Test
    void createValidFloor_fail() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        String floor = "";

        // Act
        Optional<Floor> houseFloor = roomFactory.createFloor(floor);

        // Assert
        assertTrue(houseFloor.isEmpty());
    }

    @Test
    void createValidDimensions() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        double height = 3;
        double width = 4;
        double length = 5;

        // Act
        Optional<Dimensions> dimensions = roomFactory.createDimensions(height, width, length);

        // Assert
        assertTrue(dimensions.isPresent());
    }

    @Test
    void createValidDimensions_fail() {
        // Arrange
        RoomFactory roomFactory = new RoomFactory();
        double height = -4;
        double width = 4;
        double length = 5;

        // Act
        Optional<Dimensions> dimensions = roomFactory.createDimensions(height, width, length);

        // Assert
        assertTrue(dimensions.isEmpty());
    }
}
