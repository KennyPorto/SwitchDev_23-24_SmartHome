package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.ActuatorActivityLogController;
import SmartHome.controller.rest_controllers.ActuatorController;
import SmartHome.controller.rest_controllers.DeviceController;
import SmartHome.mapper.ActuatorDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;

public class ActuatorResourceHandler
{
    public static ActuatorDTO manageAdd(ActuatorDTO actuatorDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(ActuatorController.class)
                .slash(actuatorDTO.actuatorId)
                .withSelfRel();

        Link toLogs = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ActuatorActivityLogController.class)
                                .findAllActuatorActivityLogsBySensorId(actuatorDTO.actuatorId))
                .withRel("activityLogs")
                .withType(HttpMethod.GET.name());

        Link linkToDevice = WebMvcLinkBuilder.linkTo(DeviceController.class)
                .slash(actuatorDTO.deviceId)
                .withRel("device")
                .withType(HttpMethod.GET.name());

        actuatorDTO.add(link);
        actuatorDTO.add(toLogs);
        actuatorDTO.add(linkToDevice);

        return actuatorDTO;
    }

    public static ActuatorDTO manageFindById(ActuatorDTO actuatorDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(ActuatorController.class).withSelfRel();

        Link toLogs = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ActuatorActivityLogController.class)
                                .findAllActuatorActivityLogsBySensorId(actuatorDTO.actuatorId))
                .withRel("activityLogs")
                .withType(HttpMethod.GET.name());

        Link linkToDevice = WebMvcLinkBuilder.linkTo(DeviceController.class)
                .slash(actuatorDTO.deviceId)
                .withRel("device")
                .withType(HttpMethod.GET.name());

        actuatorDTO.add(link);
        actuatorDTO.add(toLogs);
        actuatorDTO.add(linkToDevice);

        return actuatorDTO;
    }

    public static Iterable<ActuatorDTO> manageFindAll(Iterable<ActuatorDTO> actuatorDTOS)
    {
        for (ActuatorDTO actuatorDTO : actuatorDTOS) {
            Link link = WebMvcLinkBuilder.linkTo(ActuatorController.class)
                    .slash(actuatorDTO.actuatorId).withSelfRel();

            Link toLogs = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(ActuatorActivityLogController.class)
                                    .findAllActuatorActivityLogsBySensorId(actuatorDTO.actuatorId))
                    .withRel("activityLogs")
                    .withType(HttpMethod.GET.name());

            Link linkToDevice = WebMvcLinkBuilder.linkTo(DeviceController.class)
                    .slash(actuatorDTO.deviceId)
                    .withRel("device")
                    .withType(HttpMethod.GET.name());


            actuatorDTO.add(link);
            actuatorDTO.add(toLogs);
            actuatorDTO.add(linkToDevice);
        }

        return actuatorDTOS;
    }
}
