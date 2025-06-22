package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.SensorTypeController;
import SmartHome.mapper.SensorTypeDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class SensorTypeResourceHandler {
    public static SensorTypeDTO manageAdd(SensorTypeDTO sensorTypeDTO) {
        Link link = WebMvcLinkBuilder.linkTo(SensorTypeController.class)
                .slash(sensorTypeDTO.id)
                .withSelfRel();

        sensorTypeDTO.add(link);

        return sensorTypeDTO;
    }

    public static SensorTypeDTO manageFindById(SensorTypeDTO sensorTypeDTO) {
        Link link = WebMvcLinkBuilder.linkTo(SensorTypeController.class).withSelfRel();
        sensorTypeDTO.add(link);

        return sensorTypeDTO;
    }

    public static Iterable<SensorTypeDTO> manageFindAll(Iterable<SensorTypeDTO> sensorTypeDTOS) {
        for (SensorTypeDTO sensorTypeDTO : sensorTypeDTOS) {
            Link link = WebMvcLinkBuilder.linkTo(SensorTypeController.class)
                    .slash(sensorTypeDTO.id).withSelfRel();

            sensorTypeDTO.add(link);
        }

        return sensorTypeDTOS;
    }
}
