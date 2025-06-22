package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.ActuatorActivityLogController;
import SmartHome.controller.rest_controllers.ActuatorController;
import SmartHome.mapper.ActuatorActivityLogDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class ActuatorActivityLogResourceHandler
{
    public static ActuatorActivityLogDTO manageAdd(ActuatorActivityLogDTO actuatorActivityLogDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(ActuatorActivityLogController.class)
                .slash(actuatorActivityLogDTO.actuatorId)
                .withSelfRel();

        actuatorActivityLogDTO.add(link);

        Link actuatorLink = WebMvcLinkBuilder.linkTo(ActuatorController.class)
                .slash(actuatorActivityLogDTO.actuatorId).withRel("actuator");

        actuatorActivityLogDTO.add(actuatorLink);

        return actuatorActivityLogDTO;
    }

    public static ActuatorActivityLogDTO manageFindById(ActuatorActivityLogDTO actuatorActivityLogDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(ActuatorActivityLogController.class).withSelfRel();
        actuatorActivityLogDTO.add(link);

        return actuatorActivityLogDTO;
    }

    public static Iterable<ActuatorActivityLogDTO> manageFindAll(
            Iterable<ActuatorActivityLogDTO> actuatorActivityLogDTOS)
    {
        for (ActuatorActivityLogDTO actuatorActivityLogDTO : actuatorActivityLogDTOS) {
            Link link = WebMvcLinkBuilder.linkTo(ActuatorActivityLogController.class)
                    .slash(actuatorActivityLogDTO.actuatorActivityLogId).withSelfRel();

            actuatorActivityLogDTO.add(link);
        }

        return actuatorActivityLogDTOS;
    }
}
