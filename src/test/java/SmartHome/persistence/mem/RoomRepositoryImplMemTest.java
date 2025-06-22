package SmartHome.persistence.mem;

import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomRepositoryImplMemTest
{
    RoomFactory roomFactory = new RoomFactory();
    RoomName roomName = new RoomName("RoomName");
    RoomId roomId = new RoomId(1);
    HouseId houseId = new HouseId(1);
    Floor floor = new Floor("Floor1");
    Dimensions dimensions = new Dimensions(2,3,4);
    OutdoorIndoor outdoorIndoor = OutdoorIndoor.INDOOR;
    Room room = roomFactory.createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
    RoomRepositoryImplMem repo = new RoomRepositoryImplMem();
    RoomId roomIdDouble = mock(RoomId.class);
    Room roomDouble = mock(Room.class);

    @Test
    void validSaveRoom() {
        // Arrange

        // Act
        Room result = repo.save(room);

        // Assert
        assertNotNull(result);
    }

    @Test
    void validFindAllRooms() {
        // Arrange
        repo.save(room);

        // Act
        Iterable<Room> result = repo.findAll();

        // Assert
        List<Room> rooms = StreamSupport.stream(result.spliterator(), false).toList();
        assertTrue(rooms.contains(room));
    }

    @Test
    void validFindById() {
        // Arrange
        repo.save(room);

        // Act
        Optional<Room> result = repo.findById(roomId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(room, result.get());
    }

    @Test
    void invalidFindById_WhenRoomDoesNotExists() {
        // Arrange

        // Act
        Optional<Room> result = repo.findById(roomId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void validExistsById() {
        // Arrange
        repo.save(room);

        // Act
        boolean result = repo.existsById(roomId);

        // Assert
        assertTrue(result);
    }

    @Test
    void invalidExistsById() {
        // Arrange

        // Act
        boolean result = repo.existsById(roomId);

        // Assert
        assertFalse(result);
    }



    @Test
    void findAllByHouseIdNull() {
        // Arrange
        Long houseId = 1L;

        // act
        Iterable<Room> rooms = repo.findAllByHouseId(houseId);

        // assert
        assertEquals("[]", rooms.toString());
    }

    @Test
    void validSaveRoomWithIsolation() {
        // Arrange

        roomIdDouble = mock(RoomId.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);

        // Act
        Room result = repo.save(roomDouble);

        // Assert
        assertEquals(roomDouble, result);
    }

    @Test
    void validFindAllRoomsWithIsolation() {

        // Arrange
        RoomId roomIdDouble1 = mock(RoomId.class);
        Room roomDouble1 = mock(Room.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);
        when(roomDouble1.identity()).thenReturn(roomIdDouble1);
        repo.save(roomDouble);
        repo.save(roomDouble1);

        // Act
        Iterable<Room> result = repo.findAll();

        // Assert
        List<Room> rooms = StreamSupport.stream(result.spliterator(), false).toList();
        assertTrue(rooms.contains(roomDouble));
        assertTrue(rooms.contains(roomDouble1));
    }

    @Test
    void validFindByIdWithIsolation() {
        // Arrange
        roomIdDouble = mock(RoomId.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);
        repo.save(roomDouble);

        // Act
        Optional<Room> result = repo.findById(roomIdDouble);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roomDouble, result.get());
    }

    @Test
    void invalidFindById_WhenRoomDoesNotExistsWithIsolation() {
        // Arrange
        roomIdDouble = mock(RoomId.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);

        // Act
        Optional<Room> result = repo.findById(roomIdDouble);

        // Assert
        assertFalse(result.isPresent());
    }
    @Test
    void validExistsByIdWithIsolation() {
        // Arrange
        roomIdDouble = mock(RoomId.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);
        repo.save(roomDouble);

        // Act
        boolean result = repo.existsById(roomIdDouble);

        // Assert
        assertTrue(result);
    }
    @Test
    void invalidExistsByIdWithIsolation() {
        // Arrange
        roomIdDouble = mock(RoomId.class);
        when(roomDouble.identity()).thenReturn(roomIdDouble);

        // Act
        boolean result = repo.existsById(roomIdDouble);

        // Assert
        assertFalse(result);
    }
}

