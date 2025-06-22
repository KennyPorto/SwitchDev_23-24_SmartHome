package SmartHome.service;

import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.RoomDTO;
import SmartHome.mapper.RoomMapper;
import SmartHome.persistence.mem.HouseRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomServiceTest
{
    @Test
    void RoomService()
    {
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);

        RoomService service = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);
        assertNotNull(service);
    }

    @Test
    void addRoom_fail()
    {
        // Arrange
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        RoomService service = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
              () -> service.add(null, null, null, null, null, null)
        );
        String expectedMessage = "House id doesn't exist";

        // Assert
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void addRoom_success()
    {
        // Arrange
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        RoomService service = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);
        RoomName roomName = new RoomName("r1");
        RoomId roomId = new RoomId(1L);
        HouseId houseId = new HouseId(1L);
        Floor floor = new Floor("f1");
        Dimensions dimensions = new Dimensions(1.0, 1.0, 1.0);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.INDOOR;
        Room room = new RoomFactory().createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        when(houseRepositoryImplMemDouble.existsById(any())).thenReturn(true);
        when(roomFactoryDouble.createRoom(any(), any(), any(), any(), any(), any())).thenReturn(room);
        when(roomRepositoryDouble.save(any())).thenReturn(room);

        // Act
        Room result = service.add(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);
        Room expected = new RoomFactory().createRoom(roomName, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Assert
        assertTrue(result.sameAs(expected));
    }


    @Test
    void findRoomById_success()
    {
        // Arrange
        long id = 3;
        RoomId roomId = mock(RoomId.class);
        RoomFactory roomFactory = mock(RoomFactory.class);
        Room roomDouble = mock(Room.class);
        RoomRepository roomRepository = mock(RoomRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        RoomDTO roomDTO = mock(RoomDTO.class);

        when(roomFactory.createRoomId(id)).thenReturn(Optional.ofNullable(roomId));
        when(roomRepository.findById(roomId)).thenReturn(Optional.ofNullable(roomDouble));

        RoomService service = new RoomService(roomRepository, roomFactory, houseFactoryDouble, houseRepositoryImplMemDouble);

        try (MockedStatic<RoomMapper> stMapper = Mockito.mockStatic(RoomMapper.class)) {
            if (roomDouble != null) {
                stMapper.when(() -> RoomMapper.roomToDto(roomDouble))
                      .thenReturn(roomDTO);

                // Act
                RoomDTO result = RoomMapper.roomToDto(service.findById(id));

                // Assert
                assertEquals(roomDTO, result);
            }
        }
    }

    @Test
    void findById_roomIdNotFound()
    {
        // Arrange
        RoomFactory roomFactory = mock(RoomFactory.class);
        RoomRepository roomRepository = mock(RoomRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        RoomService service = new RoomService(roomRepository, roomFactory, houseFactoryDouble, houseRepositoryImplMemDouble);
        Long id = 1L;

        when(roomFactory.createRoomId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.findById(id));
    }

    @Test
    void findById_roomNotFound()
    {
        // Arrange
        RoomFactory roomFactory = mock(RoomFactory.class);
        RoomRepository roomRepository = mock(RoomRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        RoomService service = new RoomService(roomRepository, roomFactory, houseFactoryDouble, houseRepositoryImplMemDouble);
        RoomId roomId = mock(RoomId.class);
        Long id = 1L;

        when(roomFactory.createRoomId(id)).thenReturn(Optional.of(roomId));
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.findById(id));
    }

    @Test
    void roomExistsById_success()
    {
        // Arrange
        long id = 3;
        RoomId roomIdDouble = mock(RoomId.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);

        RoomService service = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);

        when(roomFactoryDouble.createRoomId(id)).thenReturn(Optional.ofNullable(roomIdDouble));
        when(roomRepositoryDouble.existsById(roomIdDouble)).thenReturn(true);

        // Act
        Pair<Boolean, RoomId> result = service.existsById(id);

        // Assert
        assertTrue(result.getLeft());
        assertEquals(roomIdDouble, result.getRight());
    }

    @Test
    void roomExistsById_fail()
    {
        // Arrange
        long id = 3;
        RoomId roomIdDouble = mock(RoomId.class);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);

        RoomService service = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);

        when(roomFactoryDouble.createRoomId(id)).thenReturn(Optional.empty());
        when(roomRepositoryDouble.existsById(roomIdDouble)).thenReturn(false);

        // Act
        Pair<Boolean, RoomId> result = service.existsById(id);

        // Assert
        assertFalse(result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void shouldFindAllByHouseId()
    {
        // Arrange
        long houseId = 1L;
        HouseId houseIdDouble = mock(HouseId.class);
        Room roomDouble = mock(Room.class);
        when(roomDouble.getHouseId()).thenReturn(houseIdDouble);
        Room room2double = mock(Room.class);
        when(room2double.getHouseId()).thenReturn(houseIdDouble);

        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        when(houseFactoryDouble.createHouseId(houseId)).thenReturn(Optional.of(houseIdDouble));
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        when(roomRepositoryDouble.findAllByHouseId(houseId)).thenReturn(List.of(roomDouble, room2double));

        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        RoomService roomService = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);
        RoomDTO roomDTO1 = mock(RoomDTO.class);
        RoomDTO roomDTO2 = mock(RoomDTO.class);

        // Mocking static method
        try (MockedStatic<RoomMapper> roomMapper = Mockito.mockStatic(RoomMapper.class)) {
            roomMapper.when(() -> RoomMapper.roomsToDto(roomRepositoryDouble.findAllByHouseId(houseId)))
                    .thenReturn(List.of(roomDTO1, roomDTO2));

            // Act
            Iterable<RoomDTO> roomsDTO = RoomMapper.roomsToDto(roomService.findAllByHouseId(houseId));
            Iterable<RoomDTO> expected = List.of();

            // Assert
            assertEquals(expected, roomsDTO);
        }
    }

    @Test
    void shouldReturnEmptyIterableForNotValidHouseId()
    {
        // Arrange
        long houseId = -1L;
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        RoomService roomService = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);
        when(houseFactoryDouble.createHouseId(houseId)).thenReturn(Optional.empty());

        // Act
        Iterable<Room> result = roomService.findAllByHouseId(houseId);
        Iterable<RoomDTO> expected = List.of();

        // Assert
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void shouldGetAllRooms()
    {
        // Arrange
        Room myRoom1Double = mock(Room.class);
        Room myRoom2Double = mock(Room.class);
        List<Room> expectedRooms = List.of(myRoom1Double, myRoom2Double);
        RoomFactory roomFactoryDouble = mock(RoomFactory.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepositoryImplMem.class);
        when(roomRepositoryDouble.findAll()).thenReturn(expectedRooms);
        HouseFactory houseFactoryDouble = mock(HouseFactory.class);
        HouseRepository houseRepositoryImplMemDouble = mock(HouseRepositoryImplMem.class);
        RoomService roomService = new RoomService(roomRepositoryDouble, roomFactoryDouble, houseFactoryDouble, houseRepositoryImplMemDouble);
        RoomDTO roomDTO1 = mock(RoomDTO.class);
        RoomDTO roomDTO2 = mock(RoomDTO.class);

        try (MockedStatic<RoomMapper> roomMapper = Mockito.mockStatic(RoomMapper.class)) {
            roomMapper.when(() -> RoomMapper.roomsToDto(expectedRooms))
                    .thenReturn(List.of(roomDTO1, roomDTO2));

            // Act
            Iterable<RoomDTO> rooms = RoomMapper.roomsToDto(roomService.findAll());
            Iterable<RoomDTO> expected = List.of(roomDTO1, roomDTO2);

            // Assert
            assertEquals(expected.toString(), rooms.toString());
        }
    }
}
