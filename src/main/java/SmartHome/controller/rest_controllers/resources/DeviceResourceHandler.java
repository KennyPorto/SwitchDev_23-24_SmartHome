package SmartHome.controller.rest_controllers.resources;

import SmartHome.controller.rest_controllers.ActuatorController;
import SmartHome.controller.rest_controllers.DeviceController;
import SmartHome.controller.rest_controllers.SensorController;
import SmartHome.mapper.ActuatorActivityLogDTO;
import SmartHome.mapper.DeviceDTO;
import SmartHome.mapper.MaxInstantTempDiffDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Set;

public class DeviceResourceHandler
{
    public static DeviceDTO manageAdd(DeviceDTO deviceDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(DeviceController.class)
              .slash(deviceDTO.deviceID)
              .withSelfRel();

        deviceDTO.add(link);

        Link linkDeactivate = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DeviceController.class)
                .deactivateDevice(deviceDTO.deviceID))
                .withRel("deactivate")
                .withType(HttpMethod.PATCH.name());

        deviceDTO.add(linkDeactivate);

        Link linkCloseBlindRollerActuator = WebMvcLinkBuilder.linkTo(DeviceController.class)
                .slash(deviceDTO.deviceID)
              .slash("/close-blinder")
                .withRel("close-blinder")
                .withType(HttpMethod.PATCH.name());

        deviceDTO.add(linkCloseBlindRollerActuator);

        Link linkGetAllLogsFromDevice = WebMvcLinkBuilder.linkTo(DeviceController.class).slash(deviceDTO.deviceID)
                .slash("start=start&end=end&delta=delta")
                .withRel("logs").withType(HttpMethod.GET.name());

        deviceDTO.add(linkGetAllLogsFromDevice);

        Link linkFindAllSensorByDevice = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SensorController.class)
                .findAllSensorsByDevice(deviceDTO.deviceID))
                .withRel("sensor")
                .withType(HttpMethod.GET.name());

        deviceDTO.add(linkFindAllSensorByDevice);

        Link linkFindAllActuatorByDevice = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ActuatorController.class)
                .findAllActuatorsByDevice(deviceDTO.deviceID))
                .withRel("actuator")
                .withType(HttpMethod.GET.name());

        deviceDTO.add(linkFindAllActuatorByDevice);

        return deviceDTO;
    }

    public static DeviceDTO manageDeactivate(DeviceDTO deviceDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(DeviceController.class)
              .slash(deviceDTO.deviceID)
              .withSelfRel();

        deviceDTO.add(link);

        return deviceDTO;
    }

    public static DeviceDTO manageFindById(DeviceDTO deviceDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(DeviceController.class)
                .withSelfRel();
        deviceDTO.add(link);

        return deviceDTO;
    }

    public static Iterable<DeviceDTO> manageFindAll(
          Iterable<DeviceDTO> deviceDTOS)
    {
        for ( DeviceDTO deviceDTO : deviceDTOS ) {
            Link link = WebMvcLinkBuilder.linkTo(DeviceController.class)
                  .slash(deviceDTO.deviceID).withSelfRel();

            deviceDTO.add(link);
        }

        return deviceDTOS;
    }

    public static MaxInstantTempDiffDTO manageMaxInstTempDiff(MaxInstantTempDiffDTO maxInstantTempDiffDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(DeviceController.class).withSelfRel();
        maxInstantTempDiffDTO.add(link);

        return maxInstantTempDiffDTO;
    }

    public static Set<Map.Entry<String, DeviceDTO>> manageFindBySensorType(Set<Map.Entry<String, DeviceDTO>> entries)
    {
        for ( Map.Entry<String, DeviceDTO> entry : entries ) {
            Link link = WebMvcLinkBuilder.linkTo(DeviceController.class).withSelfRel();
            entry.getValue().add(link);
        }

        return entries;
    }

    public static ActuatorActivityLogDTO manageCloseBlindRollerActuator(ActuatorActivityLogDTO actuatorActivityLogDTO)
    {
        Link link = WebMvcLinkBuilder.linkTo(DeviceController.class).withSelfRel();
        actuatorActivityLogDTO.add(link);

        return actuatorActivityLogDTO;
    }
}
