package SmartHome.controller.cli_controllers;

import SmartHome.domain.device.Device;
import SmartHome.mapper.DeviceDTO;
import SmartHome.mapper.DeviceMapper;
import SmartHome.service.DeviceService;

public class Ctrl06ListOfDevicesInARoom
{

    private final DeviceService _deviceService;

    public Ctrl06ListOfDevicesInARoom(DeviceService deviceService) {
        this._deviceService = deviceService;
    }

    public Iterable<DeviceDTO> getListDevicesByRoom(long roomId) {
        Iterable<Device> devices = _deviceService.findAllByRoomId(roomId);
        return DeviceMapper.deviceListToDto(devices);
    }

}
