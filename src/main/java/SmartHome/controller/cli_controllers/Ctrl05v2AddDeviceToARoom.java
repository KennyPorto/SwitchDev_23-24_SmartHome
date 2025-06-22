package SmartHome.controller.cli_controllers;

import SmartHome.domain.valueObjects.DeviceID;
import SmartHome.domain.valueObjects.DeviceModel;
import SmartHome.domain.valueObjects.DeviceName;
import SmartHome.domain.valueObjects.RoomId;
import SmartHome.mapper.DeviceDTO;
import SmartHome.mapper.DeviceMapper;
import SmartHome.service.DeviceService;

public class Ctrl05v2AddDeviceToARoom
{

    private final DeviceService _deviceService;

    public Ctrl05v2AddDeviceToARoom(DeviceService deviceService) {
        this._deviceService = deviceService;
    }

    public Iterable<DeviceDTO> addDevice(String deviceName, long deviceId, String deviceModel, long roomId) {
        if (!_deviceService.existsById(deviceId).getLeft()) {
            _deviceService.add(new DeviceName(deviceName), new DeviceID(deviceId), new DeviceModel(deviceModel), new RoomId(roomId));
        }
        return DeviceMapper.deviceListToDto(this._deviceService.findAllByRoomId(roomId));
    }

}
