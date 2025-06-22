package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.DeviceController;
import SmartHome.controller.rest_controllers.SensorActivityLogController;
import SmartHome.controller.rest_controllers.SensorController;
import SmartHome.mapper.SensorDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;

public class SensorResourceHandler
{
    public static SensorDTO manageAdd(SensorDTO sensorDTO) {
        Link link = getLinkToSensor(sensorDTO);
        Link linkToDevice = getLinkToDevice(sensorDTO.deviceId);
        Link linkToActivityLogs = getLinkToActivityLogs(sensorDTO);

        sensorDTO.add(link);
        sensorDTO.add(linkToDevice);
        sensorDTO.add(linkToActivityLogs);

        return sensorDTO;
    }

    public static SensorDTO manageFindById(SensorDTO sensorDTO) {
        Link link = WebMvcLinkBuilder.linkTo(SensorController.class).withSelfRel()
                .withType(HttpMethod.GET.name())
                .expand();
        Link linkToDevice = getLinkToDevice(sensorDTO.deviceId);
        Link linkToActivityLogs = getLinkToActivityLogs(sensorDTO);

        sensorDTO.add(link);
        sensorDTO.add(linkToDevice);
        sensorDTO.add(linkToActivityLogs);

        return sensorDTO;
    }

    public static Iterable<SensorDTO> manageFindAll(Iterable<SensorDTO> sensorDTOS) {
        for (SensorDTO sensorDTO : sensorDTOS) {
            Link link = getLinkToSensor(sensorDTO);
            Link linkToDevice = getLinkToDevice(sensorDTO.deviceId);
            Link linkToActivityLogs = getLinkToActivityLogs(sensorDTO);

            sensorDTO.add(link);
            sensorDTO.add(linkToDevice);
            sensorDTO.add(linkToActivityLogs);
        }

        return sensorDTOS;
    }

    private static Link getLinkToActivityLogs(SensorDTO sensorDTO)
    {
        return WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(SensorActivityLogController.class)
                          .findAllSensorActivityLogsBySensorId(sensorDTO.sensorId))
              .withRel("activityLogs")
              .withType(HttpMethod.GET.name());
    }

    private static Link getLinkToDevice(long deviceId)
    {
        return WebMvcLinkBuilder.linkTo(DeviceController.class)
              .slash(deviceId)
              .withRel("device")
              .withType(HttpMethod.GET.name())
              .expand();
    }

    private static Link getLinkToSensor(SensorDTO sensorDTO)
    {
        return WebMvcLinkBuilder.linkTo(SensorController.class)
              .slash(sensorDTO.sensorId)
              .withSelfRel()
              .withType(HttpMethod.GET.name())
              .expand();
    }
}
