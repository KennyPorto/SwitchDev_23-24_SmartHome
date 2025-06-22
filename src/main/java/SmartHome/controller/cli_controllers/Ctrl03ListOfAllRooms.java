package SmartHome.controller.cli_controllers;

import SmartHome.mapper.RoomDTO;
import SmartHome.mapper.RoomMapper;
import SmartHome.service.RoomService;

public class Ctrl03ListOfAllRooms
{

    private final RoomService _roomService;

    public Ctrl03ListOfAllRooms(RoomService roomService)
    {
        this._roomService = roomService;
    }

    public Iterable<RoomDTO> getAllRooms(long id)
    {

        return RoomMapper.roomsToDto(
              this._roomService.findAllByHouseId(id)
        );
    }

}
