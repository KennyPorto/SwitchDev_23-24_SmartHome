package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.ActuatorTypeController;
import SmartHome.mapper.ActuatorTypeDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class ActuatorTypeResourceHandler {
    public static ActuatorTypeDTO manageAdd(ActuatorTypeDTO actuatorTypeDTO) {
        Link link = WebMvcLinkBuilder.linkTo(ActuatorTypeController.class)
                .slash(actuatorTypeDTO.name)
                .withSelfRel();

        actuatorTypeDTO.add(link);

        return actuatorTypeDTO;
    }

    public static ActuatorTypeDTO manageFindById(ActuatorTypeDTO actuatorTypeDTO) {
        Link link = WebMvcLinkBuilder.linkTo(ActuatorTypeController.class).withSelfRel();
        actuatorTypeDTO.add(link);

        return actuatorTypeDTO;
    }

    public static Iterable<ActuatorTypeDTO> manageFindAll(Iterable<ActuatorTypeDTO> actuatorTypeDTOS) {
        for (ActuatorTypeDTO actuatorTypeDTO : actuatorTypeDTOS) {
            Link link = WebMvcLinkBuilder.linkTo(ActuatorTypeController.class)
                    .slash(actuatorTypeDTO.name).withSelfRel();

            actuatorTypeDTO.add(link);
        }

        return actuatorTypeDTOS;
    }
}
