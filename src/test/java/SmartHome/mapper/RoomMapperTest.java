package SmartHome.mapper;

import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.RoomDataModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomMapperTest {

    @Test
    void validGetRoomDTO() {
        // Arrange
        RoomName name = new RoomName("Room1");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("1");
        Dimensions dimensions = new Dimensions(2.8, 2.3, 4.7);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;

        RoomFactory roomFactory = new RoomFactory();
        Room room = roomFactory.createRoom(name, roomId, houseId, floor, dimensions, outdoorIndoor);

        // Act
        RoomDTO roomDTO = RoomMapper.roomToDto(room);

        // Assert
        assertEquals(roomId.id, roomDTO.roomId);
        assertEquals(name.name, roomDTO.roomName);
        assertEquals(houseId.id, roomDTO.houseId);
        assertEquals(floor.floor, roomDTO.houseFloor);
        assertEquals(dimensions.height, roomDTO.height);
        assertEquals(dimensions.width, roomDTO.width);
        assertEquals(dimensions.length, roomDTO.length);
    }

    @Test
    void validGetListRoomsDTO() {
        // Arrange
        List<Room> rooms = new ArrayList<>();

        RoomFactory roomFactory = new RoomFactory();
        Room room1 = roomFactory.createRoom(new RoomName("Room1"), new RoomId(1), new HouseId(1), new Floor("1"), new Dimensions(2.8, 2.3, 4.7), OutdoorIndoor.OUTDOOR);
        Room room2 = roomFactory.createRoom(new RoomName("Room2"), new RoomId(2), new HouseId(1), new Floor("2"), new Dimensions(3.8, 3.3, 5.7), OutdoorIndoor.OUTDOOR);
        rooms.add(room1);
        rooms.add(room2);

        // Act
        Iterable<RoomDTO> roomDTOList = RoomMapper.roomsToDto(rooms);

        // Assert
        assertEquals(2, ((List<RoomDTO>) roomDTOList).size());
        assertEquals(1, ((List<RoomDTO>) roomDTOList).get(0).roomId);
        assertEquals("Room1", ((List<RoomDTO>) roomDTOList).get(0).roomName);
        assertEquals(1, ((List<RoomDTO>) roomDTOList).get(0).houseId);
        assertEquals("1", ((List<RoomDTO>) roomDTOList).get(0).houseFloor);
        assertEquals(2.8, ((List<RoomDTO>) roomDTOList).get(0).height);
        assertEquals(2.3, ((List<RoomDTO>) roomDTOList).get(0).width);
        assertEquals(4.7, ((List<RoomDTO>) roomDTOList).get(0).length);
        assertEquals("OUTDOOR", ((List<RoomDTO>) roomDTOList).get(0).outdoorIndoor);
        assertEquals(2, ((List<RoomDTO>) roomDTOList).get(1).roomId);
        assertEquals("Room2", ((List<RoomDTO>) roomDTOList).get(1).roomName);
        assertEquals(1, ((List<RoomDTO>) roomDTOList).get(1).houseId);
        assertEquals("2", ((List<RoomDTO>) roomDTOList).get(1).houseFloor);
        assertEquals(3.8, ((List<RoomDTO>) roomDTOList).get(1).height);
        assertEquals(3.3, ((List<RoomDTO>) roomDTOList).get(1).width);
        assertEquals(5.7, ((List<RoomDTO>) roomDTOList).get(1).length);
        assertEquals("OUTDOOR", ((List<RoomDTO>) roomDTOList).get(1).outdoorIndoor);

    }

    @Test
    void validGetRoomDTOWithIsolation() {
        // Arrange
        Room room = mock(Room.class);
        RoomName name = new RoomName("Room1");
        RoomId roomId = new RoomId(1);
        HouseId houseId = new HouseId(1);
        Floor floor = new Floor("1");
        Dimensions dimensions = new Dimensions(2.8, 2.3, 4.7);
        OutdoorIndoor outdoorIndoor = OutdoorIndoor.OUTDOOR;
        when(room.identity()).thenReturn(roomId);
        when(room.getRoomName()).thenReturn(name);
        when(room.getHouseId()).thenReturn(houseId);
        when(room.getHouseFloor()).thenReturn(floor);
        when(room.getDimensions()).thenReturn(dimensions);
        when(room.getOutdoorIndoor()).thenReturn(outdoorIndoor);

        // Act
        RoomDTO roomDTO = RoomMapper.roomToDto(room);
        // Assert
        assertEquals(roomId.id, roomDTO.roomId);
        assertEquals(name.name, roomDTO.roomName);
        assertEquals(houseId.id, roomDTO.houseId);
        assertEquals(floor.floor, roomDTO.houseFloor);
        assertEquals(dimensions.height, roomDTO.height);
        assertEquals(dimensions.width, roomDTO.width);
        assertEquals(dimensions.length, roomDTO.length);
        assertEquals(outdoorIndoor.toString(), roomDTO.outdoorIndoor);
    }
    @Test
    void roomsDataModelToDomain() {
        // Arrange
        RoomDataModel roomDataModel = mock(RoomDataModel.class);
        when(roomDataModel.getRoomId()).thenReturn(1L);
        when(roomDataModel.getRoomName()).thenReturn("Room1");
        when(roomDataModel.getHouseId()).thenReturn(1L);
        when(roomDataModel.getFloor()).thenReturn("1");
        when(roomDataModel.getHeight()).thenReturn(2.8);
        when(roomDataModel.getWidth()).thenReturn(2.3);
        when(roomDataModel.getLength()).thenReturn(4.7);
        when(roomDataModel.getOutdoorIndoor()).thenReturn(OutdoorIndoor.OUTDOOR);
        when(roomDataModel.getVersion()).thenReturn(0);
        List<RoomDataModel> roomDataModels = new ArrayList<>();
        roomDataModels.add(roomDataModel);

        // Act
        Iterable<Room> result = RoomMapper.roomsDataModelToDomain(roomDataModels);

        // Assert
        assertEquals(1, ((List<Room>) result).size());
        assertEquals("Room1", ((List<Room>) result).get(0).getRoomName().name);
        assertEquals(1, ((List<Room>) result).get(0).identity().id);
        assertEquals(1, ((List<Room>) result).get(0).getHouseId().id);
        assertEquals("1", ((List<Room>) result).get(0).getHouseFloor().floor);
        assertEquals(2.8, ((List<Room>) result).get(0).getDimensions().height);
        assertEquals(2.3, ((List<Room>) result).get(0).getDimensions().width);
        assertEquals(4.7, ((List<Room>) result).get(0).getDimensions().length);
    }

    @Test
    void roomDataModelToDomain() {
        // Arrange
        RoomDataModel roomDataModel = mock(RoomDataModel.class);
        when(roomDataModel.getRoomId()).thenReturn(1L);
        when(roomDataModel.getRoomName()).thenReturn("Room1");
        when(roomDataModel.getHouseId()).thenReturn(1L);
        when(roomDataModel.getFloor()).thenReturn("1");
        when(roomDataModel.getHeight()).thenReturn(2.8);
        when(roomDataModel.getWidth()).thenReturn(2.3);
        when(roomDataModel.getLength()).thenReturn(4.7);
        when(roomDataModel.getOutdoorIndoor()).thenReturn(OutdoorIndoor.OUTDOOR);
        when(roomDataModel.getVersion()).thenReturn(0);

        // Act
        Room result = RoomMapper.roomDataModelToDomain(roomDataModel);

        // Assert
        assertEquals("Room1", result.getRoomName().name);
        assertEquals(1, result.identity().id);
        assertEquals(1, result.getHouseId().id);
        assertEquals("1", result.getHouseFloor().floor);
        assertEquals(2.8, result.getDimensions().height);
        assertEquals(2.3, result.getDimensions().width);
        assertEquals(4.7, result.getDimensions().length);
    }
}
