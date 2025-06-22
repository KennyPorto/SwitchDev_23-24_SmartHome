package SmartHome.mapper;

import SmartHome.domain.room.Room;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.RoomDataModel;

import java.util.ArrayList;
import java.util.List;

public class RoomMapper
{

    public static RoomDTO roomToDto(Room room)
    {
        String roomName = room.getRoomName().name;
        long id = room.identity().id;
        long houseId = room.getHouseId().id;
        String houseFloor = room.getHouseFloor().floor;
        double height = room.getDimensions().height;
        double width = room.getDimensions().width;
        double length = room.getDimensions().length;
        String outdoorIndoor = room.getOutdoorIndoor().toString();
        return new RoomDTO(roomName, id, houseId, houseFloor, height, width, length, outdoorIndoor);
    }

    public static Iterable<RoomDTO> roomsToDto(Iterable<Room> rooms)
    {
        List<RoomDTO> roomListDTO = new ArrayList<>();
        rooms.forEach(room ->
        {
            RoomDTO roomDTO = RoomMapper.roomToDto(room);
            roomListDTO.add(roomDTO);
        });

        return roomListDTO;
    }

    public static Iterable<Room> roomsDataModelToDomain(Iterable<RoomDataModel> roomDataModels)
    {
        List<Room> rooms = new ArrayList<>();

        for ( RoomDataModel roomDm : roomDataModels ) {
            rooms.add(roomDataModelToDomain(roomDm));
        }

        return rooms;
    }

    public static Room roomDataModelToDomain(RoomDataModel roomDataModel)
    {
        RoomName roomName = new RoomName(roomDataModel.getRoomName());
        RoomId id = new RoomId(roomDataModel.getRoomId());
        HouseId houseId = new HouseId(roomDataModel.getHouseId());
        Floor houseFloor = new Floor(roomDataModel.getFloor());
        Dimensions dimensions = new Dimensions(roomDataModel.getHeight(), roomDataModel.getWidth(), roomDataModel.getLength());
        OutdoorIndoor outdoorIndoor = roomDataModel.getOutdoorIndoor();
        return new RoomFactory().createRoom(roomName, id, houseId, houseFloor, dimensions, outdoorIndoor);

    }

}
