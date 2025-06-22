package SmartHome.controller.cli_controllers;

import SmartHome.mapper.DeviceDTO;
import SmartHome.service.DevFuncService;

import java.util.Map;
import java.util.Set;

public class Ctrl09HouseDevicesByFunctionality
{
    private final DevFuncService _devFuncService;

    public Ctrl09HouseDevicesByFunctionality(DevFuncService devFuncService)
    {
        this._devFuncService = devFuncService;
    }

    public Set<Map.Entry<String, DeviceDTO>> getDevicesByRoomAndFunctionality(long houseId) {
        return _devFuncService.getData(houseId);
    }
}
