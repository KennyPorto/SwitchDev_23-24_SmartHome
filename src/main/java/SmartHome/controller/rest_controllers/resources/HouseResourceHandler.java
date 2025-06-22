package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.DeviceController;
import SmartHome.controller.rest_controllers.HouseController;
import SmartHome.controller.rest_controllers.RoomController;
import SmartHome.mapper.HouseDTO;
import SmartHome.mapper.PeakPowerDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;

public class HouseResourceHandler
{

    public static HouseDTO manageAddAndPut(HouseDTO houseDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(HouseController.class)
                .slash(houseDTO.houseId)
                .withSelfRel();

        houseDTO.add(link);

        Link linkFindPeakPower = WebMvcLinkBuilder.linkTo(HouseController.class).slash(houseDTO.houseId)
                .slash("peak-power?start-date={{start-date}}&end-date={{end-date}}&delta={{delta}}")
                .withRel("peak-power").withType(HttpMethod.GET.name());

        houseDTO.add(linkFindPeakPower);

        Link linkDevicesByFunc = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DeviceController.class)
                        .getDevicesByRoomAndSensorType(houseDTO.houseId))
                .withRel("functionalities")
                .withType(HttpMethod.GET.name())
                .expand();

        houseDTO.add(linkDevicesByFunc);

        Link linkRoomsByHouseId = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoomController.class)
                        .findAllByHouseId(houseDTO.houseId))
                .withRel("rooms")
                .withType(HttpMethod.GET.name())
                .expand();

        houseDTO.add(linkRoomsByHouseId);

        return houseDTO;
    }

    public static HouseDTO manageFindById(HouseDTO houseDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(HouseController.class).withSelfRel();

        houseDTO.add(link);

        return houseDTO;
    }

    public static Iterable<HouseDTO> manageFindAll(Iterable<HouseDTO> houses)
    {
        for (HouseDTO houseDTO : houses) {
            Link link = WebMvcLinkBuilder.linkTo(HouseController.class)
                    .slash(houseDTO.houseId).withSelfRel();

            houseDTO.add(link);
        }

        return houses;
    }

    public static PeakPowerDTO managePeakPower(long houseId, PeakPowerDTO peakPowerDTO) {
        Link link = WebMvcLinkBuilder.linkTo(HouseController.class)
                .slash(houseId)
                .slash("peak-power")
                .withSelfRel();

        peakPowerDTO.add(link);

        return peakPowerDTO;
    }


}
