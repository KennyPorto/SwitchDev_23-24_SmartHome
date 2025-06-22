package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.RoomResourceHandler;
import SmartHome.domain.room.Room;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.RoomDTO;
import SmartHome.mapper.RoomMapper;
import SmartHome.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController
{
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO dto)
    {
        Room room = roomService.add(
                new RoomName(dto.roomName),
                new RoomId(dto.roomId),
                new HouseId(dto.houseId),
                new Floor(dto.houseFloor),
                new Dimensions(dto.height, dto.width, dto.length),
                OutdoorIndoor.valueOf(dto.outdoorIndoor));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                RoomResourceHandler.manageAdd(RoomMapper.roomToDto(room)));
    }

    @GetMapping("/{room-id}")
    public ResponseEntity<RoomDTO> findRoomById(@PathVariable("room-id") long roomId)
    {
        Room room = roomService.findById(roomId);
        return ResponseEntity.status(HttpStatus.OK).
              body(RoomResourceHandler.manageFindById(
                        RoomMapper.roomToDto(room)
              ));
    }

    @GetMapping
    public ResponseEntity<Iterable<RoomDTO>> findAllRooms()
    {
        Iterable<Room> rooms = roomService.findAll();
        Iterable<RoomDTO> roomDTOS = RoomResourceHandler.manageFindAll(
              RoomMapper.roomsToDto(rooms)
        );

        return ResponseEntity.status(HttpStatus.OK).body(roomDTOS);
    }

    @GetMapping(params = "house-id")
    public ResponseEntity<Iterable<RoomDTO>> findAllByHouseId(@RequestParam("house-id") long houseId)
    {
        Iterable<Room> rooms = roomService.findAllByHouseId(houseId);
        Iterable<RoomDTO> roomDTOS = RoomResourceHandler.manageFindAll(
              RoomMapper.roomsToDto(rooms)
        );

        return ResponseEntity.status(HttpStatus.OK).body(roomDTOS);
    }
}
