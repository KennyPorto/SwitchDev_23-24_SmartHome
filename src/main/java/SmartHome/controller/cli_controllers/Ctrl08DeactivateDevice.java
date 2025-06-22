package SmartHome.controller.cli_controllers;

import SmartHome.domain.device.Device;
import SmartHome.service.DeviceService;

public class Ctrl08DeactivateDevice
{
    private final DeviceService _deviceService;

    public Ctrl08DeactivateDevice(DeviceService deviceService) {
        _deviceService = deviceService;
    }

    public Device deactivateDevice(long deviceId)
    {
        return _deviceService.deactivateDevice(deviceId);
    }
}
