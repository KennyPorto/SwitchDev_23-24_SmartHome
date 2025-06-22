package SmartHome.controller.cli_controllers;

import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.RoomDTO;
import SmartHome.service.RoomService;

public class Ctrl02AddRoomToHouse
{
    private final RoomService _roomService;

    public Ctrl02AddRoomToHouse(RoomService roomService)
    {
        this._roomService = roomService;
    }

    public boolean addRoomToHouse(RoomDTO dto)
    {
        if ( dto == null ) return false;

        try {
            this._roomService.add(
                  new RoomName(dto.roomName),
                  new RoomId(dto.roomId),
                  new HouseId(dto.houseId),
                  new Floor(dto.houseFloor),
                  new Dimensions(dto.height, dto.width, dto.length),
                  OutdoorIndoor.valueOf(dto.outdoorIndoor)
            );
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }
}
