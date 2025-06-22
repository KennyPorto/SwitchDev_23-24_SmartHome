package SmartHome.mapper;

import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.DeviceDataModel;

import java.util.ArrayList;
import java.util.List;

public class DeviceMapper
{
    public static DeviceDTO deviceToDto(Device device)
    {
        return new DeviceDTO(device.getRoomId().id, device.identity().id, device.getName().name, device.getModel().model,
                device.getDeviceStatus().activated);
    }

    public static DeviceDTO deviceToDtoIsActive(Device device)
    {
        return new DeviceDTO(device.getRoomId().id, device.identity().id, device.getName().name, device.getModel().model, device.getDeviceStatus().activated);
    }

    public static Iterable<DeviceDTO> deviceListToDto(Iterable<Device> devices)
    {
        List<DeviceDTO> deviceListDTO = new ArrayList<>();

        devices.forEach(device -> {
            DeviceDTO deviceDTO = DeviceMapper.deviceToDto(device);
            deviceListDTO.add(deviceDTO);
        });

        return deviceListDTO;
    }

    public static Device deviceDataModelToDomain(DeviceDataModel deviceDataModel)
    {
        DeviceFactory deviceFactory = new DeviceFactory();
        RoomId roomId = new RoomId(deviceDataModel.getRoomId());
        DeviceID deviceID = new DeviceID(deviceDataModel.getId());
        DeviceName deviceName = new DeviceName(deviceDataModel.getDeviceName());
        DeviceModel deviceModel = new DeviceModel(deviceDataModel.getDeviceModel());
        DeviceStatus deviceStatus = new DeviceStatus(deviceDataModel.isDeviceStatus());
        return deviceFactory.createDevice(roomId, deviceID, deviceName, deviceModel, deviceStatus);
    }

    public static Iterable<Device> devicesDataModelToDomain(Iterable<DeviceDataModel> deviceDataModels)
    {
        List<Device> devices = new ArrayList<>();
        deviceDataModels.forEach(
              dataModel -> devices.add(deviceDataModelToDomain(dataModel))
        );
        return devices;
    }

}






