package SmartHome.domain.device;

import SmartHome.domain.valueObjects.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceFactory
{

    public Device createDevice(RoomId roomId, DeviceID deviceID, DeviceName deviceName, DeviceModel deviceModel,
                               DeviceStatus deviceStatus)
    {
        return new Device(roomId, deviceID, deviceName, deviceModel, deviceStatus, this);
    }

    public Optional<DeviceID> createDeviceId(long id)
    {
        try {
            return Optional.of(new DeviceID(id));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<DeviceName> createDeviceName(String name)
    {
        try {
            return Optional.of(new DeviceName(name));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<DeviceModel> createDeviceModel(String model)
    {
        try {
            return Optional.of(new DeviceModel(model));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public DeviceStatus createDeviceStatus(boolean activated)
    {
        return new DeviceStatus(activated);
    }
}

