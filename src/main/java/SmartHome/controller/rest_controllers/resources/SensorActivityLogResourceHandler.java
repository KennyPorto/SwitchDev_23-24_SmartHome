package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.SensorActivityLogController;
import SmartHome.controller.rest_controllers.SensorController;
import SmartHome.mapper.SensorActivityLogDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;

public class SensorActivityLogResourceHandler {
    public static SensorActivityLogDTO manageAdd(SensorActivityLogDTO sensorActivityLogDTO) {
        Link link = WebMvcLinkBuilder.linkTo(SensorActivityLogController.class)
                .slash(sensorActivityLogDTO.sensorActivityLogId)
                .withSelfRel()
                .withType(HttpMethod.GET.name())
                .expand();

        Link sensorLink = WebMvcLinkBuilder.linkTo(SensorController.class)
                .slash(sensorActivityLogDTO.sensorActivityLogId).withRel("sensor");

        sensorActivityLogDTO.add(link);
        sensorActivityLogDTO.add(sensorLink);

        return sensorActivityLogDTO;
    }

    public static SensorActivityLogDTO manageFindById(SensorActivityLogDTO sensorActivityLogDTO) {
        Link link = WebMvcLinkBuilder.linkTo(SensorActivityLogController.class)
                .withSelfRel()
                .withType(HttpMethod.GET.name())
                .expand();
        Link sensorLink = WebMvcLinkBuilder.linkTo(SensorController.class)
                .slash(sensorActivityLogDTO.sensorActivityLogId).withRel("sensor");

        sensorActivityLogDTO.add(link);
        sensorActivityLogDTO.add(sensorLink);

        return sensorActivityLogDTO;
    }

    public static Iterable<SensorActivityLogDTO> manageFindAll(
            Iterable<SensorActivityLogDTO> sensorActivityLogDTOS) {
        for (SensorActivityLogDTO sensorActivityLogDTO : sensorActivityLogDTOS) {
            Link link = WebMvcLinkBuilder.linkTo(SensorActivityLogController.class)
                    .slash(sensorActivityLogDTO.sensorActivityLogId).withSelfRel()
                    .withType(HttpMethod.GET.name())
                    .expand();
            Link sensorLink = WebMvcLinkBuilder.linkTo(SensorController.class)
                    .slash(sensorActivityLogDTO.sensorActivityLogId).withRel("sensor");

            sensorActivityLogDTO.add(link);
            sensorActivityLogDTO.add(sensorLink);
        }

        return sensorActivityLogDTOS;
    }
}
