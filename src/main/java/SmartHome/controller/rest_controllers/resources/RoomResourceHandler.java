package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.DeviceController;
import SmartHome.controller.rest_controllers.RoomController;
import SmartHome.mapper.RoomDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;

public class RoomResourceHandler
{
    public static RoomDTO manageAdd(RoomDTO roomDTO)
    {
        Link link = getRoomLink(roomDTO);
        roomDTO.add(link);

        Link linkToDevices = getDevicesInRoomLink(roomDTO);
        roomDTO.add(linkToDevices);
        return roomDTO;
    }

    public static RoomDTO manageFindById(RoomDTO roomDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(RoomController.class).withSelfRel();
        Link linkToDevices = getDevicesInRoomLink(roomDTO);

        roomDTO.add(link);
        roomDTO.add(linkToDevices);

        return roomDTO;
    }

    public static Iterable<RoomDTO> manageFindAll(
          Iterable<RoomDTO> rooms)
    {
        for ( RoomDTO roomDTO : rooms ) {
            Link link = getRoomLink(roomDTO);
            roomDTO.add(link);
            Link linkToDevices = getDevicesInRoomLink(roomDTO);
            roomDTO.add(linkToDevices);
        }

        return rooms;
    }

    private static Link getDevicesInRoomLink(RoomDTO roomDTO)
    {
        return WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(DeviceController.class)
                          .findAllDevicesByRoom(roomDTO.roomId))
              .withRel("devices-in-room")
              .withType(HttpMethod.GET.name());
    }

    private static Link getRoomLink(RoomDTO roomDTO)
    {
        return WebMvcLinkBuilder.linkTo(RoomController.class)
              .slash(roomDTO.roomId).withSelfRel();
    }
}
